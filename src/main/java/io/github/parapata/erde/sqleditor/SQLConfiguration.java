package io.github.parapata.erde.sqleditor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import io.github.parapata.erde.ERDPlugin;
import io.github.parapata.erde.preference.ERDPreferenceKey;

/**
 * SQLConfiguration.
 *
 * @author modified by parapata
 */
public class SQLConfiguration extends SourceViewerConfiguration {

    @Override
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
        return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
                SQLPartitionScanner.SQL_COMMENT,
                SQLPartitionScanner.SQL_STRING };
    }

    @Override
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {

        PresentationReconciler reconciler = new PresentationReconciler();

        DefaultDamagerRepairer commentDR = new DefaultDamagerRepairer(getCommentScanner());
        reconciler.setDamager(commentDR, SQLPartitionScanner.SQL_COMMENT);
        reconciler.setRepairer(commentDR, SQLPartitionScanner.SQL_COMMENT);

        DefaultDamagerRepairer stringDR = new DefaultDamagerRepairer(getStringScanner());
        reconciler.setDamager(stringDR, SQLPartitionScanner.SQL_STRING);
        reconciler.setRepairer(stringDR, SQLPartitionScanner.SQL_STRING);

        DefaultDamagerRepairer keywordDR = new DefaultDamagerRepairer(getDefaultScanner());
        reconciler.setDamager(keywordDR, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(keywordDR, IDocument.DEFAULT_CONTENT_TYPE);

        return reconciler;
    }

    private RuleBasedScanner getCommentScanner() {
        RuleBasedScanner scanner = new RuleBasedScanner();
        EditorColorProvider colorProvider = ERDPlugin.getDefault().getEditorColorProvider();
        scanner.setDefaultReturnToken(
                colorProvider.getToken(ERDPreferenceKey.COLOR_COMMENT));
        return scanner;
    }

    private RuleBasedScanner getStringScanner() {
        RuleBasedScanner scanner = new RuleBasedScanner();
        EditorColorProvider colorProvider = ERDPlugin.getDefault().getEditorColorProvider();
        scanner.setDefaultReturnToken(
                colorProvider.getToken(ERDPreferenceKey.COLOR_STRING));
        return scanner;
    }

    private RuleBasedScanner getDefaultScanner() {
        return new SQLKeywordPartitionScanner();
    }
}
