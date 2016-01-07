package com.atlantbh.jmeter.plugins.jtlresultsparser.gui;

import com.atlantbh.jmeter.plugins.jtlresultsparser.JtlResultsParser;
import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.visualizers.gui.AbstractListenerGui;
import org.apache.jorphan.gui.JLabeledTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by adnan on 07/01/16.
 */
public class JtlResultsParserGui extends AbstractListenerGui {

    private JLabeledTextField fileNameTextField;
    private String lastPath = ".";

    public JtlResultsParserGui() {
        super();
        init();
    }

    @Override
    public void clearGui(){
        super.clearGui();
    }

    @Override
    public String getStaticLabel() {
        return "JTL Results Parser";
    }

    @Override
    public String getLabelResource() {
        return "JTL Results Parser";
    }

    @Override
    public TestElement createTestElement() {
        JtlResultsParser testElement = new JtlResultsParser();
        modifyTestElement(testElement);
        return testElement;
    }

    @Override
    public void modifyTestElement(TestElement te) {
        super.configureTestElement(te);
        if (te instanceof JtlResultsParser ) {
            JtlResultsParser jtlParserLogger = (JtlResultsParser) te;
            jtlParserLogger.setOutputFile(fileNameTextField.getText());
        }
    }
    @Override
    public void configure(TestElement testElement) {
        super.configure(testElement);
        if (testElement instanceof JtlResultsParser) {
            JtlResultsParser jtlParserLogger = (JtlResultsParser) testElement;
            fileNameTextField.setText(jtlParserLogger.getOutputFile());
        }
    }
    public void init() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());

        JButton browseButton = new JButton("Browse...");
        browseButton.addActionListener(new FileBrowserAction());
        fileNameTextField = new JLabeledTextField("Filename");

        GridBagConstraints gridBagConstraints = getGridBagConstraints();
        panel.add(fileNameTextField, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        panel.add(browseButton, gridBagConstraints);

        add(panel);
    }

    private GridBagConstraints getGridBagConstraints() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        return gridBagConstraints;
    }

    private class FileBrowserAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser(lastPath);
            if (chooser != null) {
                if(GuiPackage.getInstance() != null) {
                    int returnVal = chooser.showOpenDialog(GuiPackage.getInstance().getMainFrame());
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        fileNameTextField.setText(chooser.getSelectedFile().getPath());
                    }
                    lastPath = chooser.getCurrentDirectory().getPath();
                }
            }
        }

    }

    private void addToPanel(JPanel panel, GridBagConstraints constraints, int col, int row, JComponent component) {
        constraints.gridx = col;
        constraints.gridy = row;
        panel.add(component, constraints);
    }
}
