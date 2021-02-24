package io.github.erde.generate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import io.github.erde.Activator;

/**
 * GeneratorProvider.
 *
 * @author modified by parapata
 */
public class GeneratorProvider {

    private static List<IGenerator> contributedGenerators = null;

    public static List<IGenerator> getGeneraters() {
        if (contributedGenerators == null) {
            // load contributed generators
            contributedGenerators = new ArrayList<>();

            IExtensionRegistry registry = Platform.getExtensionRegistry();
            IExtensionPoint point = registry.getExtensionPoint(Activator.PLUGIN_ID + ".generators");
            IExtension[] extensions = point.getExtensions();

            for (IExtension extension : extensions) {
                IConfigurationElement[] elements = extension.getConfigurationElements();
                for (IConfigurationElement element : elements) {
                    try {
                        if ("generator".equals(element.getName())) {
                            IGenerator generator = (IGenerator) element.createExecutableExtension("class");
                            contributedGenerators.add(generator);
                        }
                    } catch (Exception e) {
                        Activator.logException(e);
                    }
                }
            }
        }
        return contributedGenerators;
    }
}
