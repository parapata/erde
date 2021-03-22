package io.github.erde;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import io.github.erde.core.util.swt.ColorRegistry;
import io.github.erde.dialect.IDialect;
import io.github.erde.sqleditor.EditorColorProvider;

/**
 * Activator The main plugin class to be used in the desktop.
 *
 * @author modified by parapata
 */
public class Activator extends AbstractUIPlugin {

    public static final String PLUGIN_ID = Activator.class.getPackageName();

    public static final String EXTENSION_ERDE = ".ere";
    public static final String EXTENSION_DDL = ".ddl";

    // validation levels
    public static final String LEVEL_ERROR = "ERROR";
    public static final String LEVEL_WARNING = "WARNING";
    public static final String LEVEL_IGNORE = "IGNORE";

    // The shared instance.
    private static Activator plugin;

    /**
     * Returns the shared instance.
     */
    public static Activator getDefault() {
        return plugin;
    }

    public static String getCharset() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject project = workspace.getRoot().getProject();
        try {
            if (project != null) {
                return project.getDefaultCharset();
            }
        } catch (CoreException e) {
        }
        return StandardCharsets.UTF_8.name();
    }

    /**
     * Returns a <code>Image</code> by a given path.
     * <p>
     * Created <code>Image</code> is cached by the <code>ImageRegistry</code>. If <code>ImageRegistry</code> already has
     * cached <code>Image</code>, this method returns cached <code>Image</code>.
     * <p>
     * Cached images are disposed at {@link Activator#stop(BundleContext)}.
     *
     * @param path
     * @return
     */
    public static Image getImage(String path) {
        ImageRegistry images = getDefault().getImageRegistry();
        Image image = images.get(path);
        if (image == null) {
            image = getImageDescriptor(path).createImage();
            images.put(path, image);
        }
        return image;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in relative path.
     *
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    public static void logException(Exception e) {
        IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, "Error", e);
        getDefault().getLog().log(status);
    }

    private Map<String, IDialect> contributedDialects = null;

    private ColorRegistry colorRegistry = new ColorRegistry();

    private EditorColorProvider colorProvider;

    /**
     * The constructor.
     */
    public Activator() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation.
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        this.colorProvider = new EditorColorProvider(getPreferenceStore());
    }

    /**
     * This method is called when the plug-in is stopped.
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);

        Display.getDefault().syncExec(() -> {
            getImageRegistry().dispose();
            colorRegistry.dispose();
        });

        plugin = null;
    }

    public Color getColor(RGB rgb) {
        return colorRegistry.getColor(rgb);
    }

    public EditorColorProvider getEditorColorProvider() {
        return this.colorProvider;
    }

    /**
     * Returns contributed <code>IDialect</code>s.
     *
     * @return contributed dialects.
     */
    public Map<String, IDialect> getContributedDialects() {
        if (this.contributedDialects == null) {
            IExtensionRegistry registry = Platform.getExtensionRegistry();
            IExtensionPoint point = registry.getExtensionPoint(String.format("%s.dialects", PLUGIN_ID));
            IExtension[] extensions = point.getExtensions();
            this.contributedDialects = new HashMap<>();

            for (IExtension extension : extensions) {
                IConfigurationElement[] elements = extension.getConfigurationElements();
                for (IConfigurationElement element : elements) {
                    try {
                        if ("dialect".equals(element.getName())) {
                            String name = element.getAttribute("name");
                            IDialect dialect = (IDialect) element.createExecutableExtension("class");
                            this.contributedDialects.put(name, dialect);
                        }
                    } catch (Exception e) {
                        logException(e);
                    }
                }
            }
        }
        return this.contributedDialects;
    }
}
