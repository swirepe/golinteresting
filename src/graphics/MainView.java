/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainView.java
 *
 * Created on Oct 29, 2010, 1:58:38 PM
 */

package graphics;

import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;

import loader.EncodedLoader;
import rule.DayAndNightRule;
import rule.GenericRule;
import rule.ProbabalisticRule;
import rule.SeedsRule;
import rule.StandardRule;
import rule.TwoAwayRule;
import board.Board;
import board.InfiniteBoard;
import boardgenerators.Box;
import boardgenerators.Glider;
import boardgenerators.GliderGun;

/**
 *
 * @author pkehrer
 */
@SuppressWarnings("serial")
public class MainView extends javax.swing.JFrame {
	
	private Timer timer;
	private BoardView boardView;
	private Board board;
	int height = 30;
	int width = 50;
	
	public void goButtonPress() {
		this.goButton.setEnabled(false);

		ruleSelection();
		
		int delayVal = 50;
		if (this.speedComboBox.getSelectedItem().equals("Fast")) {
			delayVal = 20;
		} else if (this.speedComboBox.getSelectedItem().equals("Medium")) {
			delayVal = 50;
		} else if (this.speedComboBox.getSelectedItem().equals("Slow")) {
			delayVal = 100;
		}
		
		timer = new Timer(delayVal, new UpdateListener());
        timer.start();

	}
	
	private class UpdateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
					int planned = Integer.parseInt(iterationTextBox.getText());
				if (boardView.iterations >= planned && planned != 0) {
					timer.stop();
					goButton.setEnabled(true);
				} else {
					board.runOnce();
					boardView.setCells();
					iterationsLabel.setText(board.getIterations()+"");	
					heuristicLabel.setText(board.getHeuristicValue()+"");
				}
			} catch (NumberFormatException ex) {
				board.runOnce();
				boardView.setCells();
				iterationsLabel.setText(board.getIterations()+"");
				heuristicLabel.setText(board.getHeuristicValue()+"");
			}
		}
	}
	
	public void stopButtonPress() {
		this.goButton.setEnabled(true);
		if (timer != null) {
			timer.stop();
		}
	}
	   
    public void designSelection() {
		String designString = (String)this.designComboBox.getSelectedItem();
		if (designString.equals("Glider")) {
			board = new Glider().getBoard(height,width);
		} else if (designString.equals("Glider Gun")) {
			board = new GliderGun().getBoard(height, width);
		} else if (designString.equals("Box")) {
			board = new Box().getBoard(height, width);
		} else if (designString.equals("<Blank>")) {
			//board = new Blank().getBoard(height, width);
			board = new InfiniteBoard(height, width);
		} else {
			board = null;
		}
		ruleSelection();
		refreshBoard();

    }
    
    public void ruleSelection() {
		String ruleString = (String)this.ruleComboBox.getSelectedItem();
		
		if (!ruleString.equals("Custom")) {
			setCustomPanelEnabled(false);
		}
		
		if (ruleString.equals("Seeds")) {
			board.setRule(new SeedsRule(board));
		} else if (ruleString.equals("Conway's")) {
			board.setRule(new StandardRule(board));
		} else if (ruleString.equals("Probabalistic")) {
			board.setRule(new ProbabalisticRule(board));
		} else if (ruleString.equals("Two Away")) {
			board.setRule(new TwoAwayRule(board));
		} else if (ruleString.equals("Day And Night")) {
			board.setRule(new DayAndNightRule(board));
		} else if (ruleString.equals("Custom")) {
			setCustomPanelEnabled(true);
			board.setRule(makeCustomRule());
		}
    }
    
    public GenericRule makeCustomRule() {
    	List<Integer> survives = new ArrayList<Integer>();
    	if(s0.isSelected())
    		survives.add(0);
    	if (s1.isSelected())
    		survives.add(1);
    	if (s2.isSelected())
    		survives.add(2);
    	if (s3.isSelected())
    		survives.add(3);
    	if (s4.isSelected())
    		survives.add(4);
    	if (s5.isSelected())
    		survives.add(5);
    	if (s6.isSelected())
    		survives.add(6);
    	if (s7.isSelected())
    		survives.add(7);
    	if (s8.isSelected())
    		survives.add(8);
    	
    	List<Integer> born = new ArrayList<Integer>();
    	if (b0.isSelected())
    		born.add(0);
    	if (b1.isSelected())
    		born.add(1);
    	if (b2.isSelected())
    		born.add(2);
    	if (b3.isSelected())
    		born.add(3);
    	if (b4.isSelected())
    		born.add(4);
    	if (b5.isSelected())
    		born.add(5);
    	if (b6.isSelected())
    		born.add(6);
    	if (b7.isSelected())
    		born.add(7);
    	if (b8.isSelected())
    		born.add(8);
    	
    	return new GenericRule(survives, born, board);
    }
    
    public void refreshBoard() {
		if (boardView != null) {
			boardPanel.getComponent(0).setVisible(false);
		}

		boardView = new BoardView(board, true);
		this.boardPanel.setLayout(new FlowLayout());
		this.boardPanel.add(boardView,0);
		iterationsLabel.setText(board.getIterations()+"");
		heuristicLabel.setText(board.getHeuristicValue()+"");
		boardPanel.revalidate();
    }

    public void saveButtonPressed() {
    	FileDialog fd = new FileDialog((Frame) this, "Save Board", FileDialog.SAVE);
    	fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".gol");
			}
    		
    	});
    	fd.setVisible(true);
    	String filename = fd.getDirectory() + fd.getFile();
    	if (filename.contains(".") && !filename.endsWith(".gol")) {
    		JOptionPane.showMessageDialog(this, "Sorry, but filename must end in .gol");
    	} else {
    		if (!filename.endsWith(".gol")) {
    			filename = filename+".gol";
    		}
    		board.getPrinter().print(filename);
    	}
    	
    }
    
    public void recallButtonPressed() {
    	FileDialog fd = new FileDialog((Frame) this, "Save Board", FileDialog.LOAD);
    	fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".gol");
			}
    		
    	});
    	fd.setVisible(true);
    	
    	String filename = fd.getDirectory()+fd.getFile();
    	this.board = new EncodedLoader().load(filename);
    	refreshBoard();
    }
    
   
    
    public void resetButtonPressed() {
    	//this.board = new Blank().getBoard(height, width);
    	//refreshBoard();
    	designSelection();
    }
    
    public void setCustomPanelEnabled(boolean enabled) {
    	survivesLabel.setEnabled(enabled);
    	s0.setEnabled(enabled);
    	s1.setEnabled(enabled);
    	s2.setEnabled(enabled);
    	s3.setEnabled(enabled);
    	s4.setEnabled(enabled);
    	s5.setEnabled(enabled);
    	s6.setEnabled(enabled);
    	s7.setEnabled(enabled);
    	s8.setEnabled(enabled);
    	bornLabel.setEnabled(enabled);
    	b0.setEnabled(enabled);
    	b1.setEnabled(enabled);
    	b2.setEnabled(enabled);
    	b3.setEnabled(enabled);
    	b4.setEnabled(enabled);
    	b5.setEnabled(enabled);
    	b6.setEnabled(enabled);
    	b7.setEnabled(enabled);
    	b8.setEnabled(enabled);
    }
    
    /** Creates new form MainView */
    public MainView() {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
        initComponents();
        designSelection();
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        boardPanel = new javax.swing.JPanel();
        goButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        designComboBox = new java.awt.Choice();
        designComboBox.add("Glider");
        designComboBox.add("Glider Gun");
        designComboBox.add("Box");
        designComboBox.add("<Blank>");
        ruleComboBox = new java.awt.Choice();
        ruleComboBox.add("Conway's");
        ruleComboBox.add("Seeds");
        ruleComboBox.add("Probabalistic");
        ruleComboBox.add("Two Away");
        ruleComboBox.add("Day And Night");
        ruleComboBox.add("Custom");
        jLabel2 = new javax.swing.JLabel();
        iterationsLabel = new javax.swing.JLabel();
        iterationTextBox = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        speedComboBox = new java.awt.Choice();
        speedComboBox.add("Fast");
        speedComboBox.add("Medium");
        speedComboBox.add("Slow");
        saveButton = new javax.swing.JButton();
        recallButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        s2 = new javax.swing.JCheckBox();
        s1 = new javax.swing.JCheckBox();
        s8 = new javax.swing.JCheckBox();
        s3 = new javax.swing.JCheckBox();
        s4 = new javax.swing.JCheckBox();
        s5 = new javax.swing.JCheckBox();
        s6 = new javax.swing.JCheckBox();
        s7 = new javax.swing.JCheckBox();
        b8 = new javax.swing.JCheckBox();
        b7 = new javax.swing.JCheckBox();
        b6 = new javax.swing.JCheckBox();
        b5 = new javax.swing.JCheckBox();
        b4 = new javax.swing.JCheckBox();
        b3 = new javax.swing.JCheckBox();
        b2 = new javax.swing.JCheckBox();
        b1 = new javax.swing.JCheckBox();
        survivesLabel = new javax.swing.JLabel();
        bornLabel = new javax.swing.JLabel();
        s0 = new javax.swing.JCheckBox();
        b0 = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        heuristicLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("Spawn Rule:");

        jLabel4.setText("Design:");

        boardPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        org.jdesktop.layout.GroupLayout boardPanelLayout = new org.jdesktop.layout.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 594, Short.MAX_VALUE)
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 370, Short.MAX_VALUE)
        );

        goButton.setBackground(new java.awt.Color(255, 255, 255));
        goButton.setText("Go!");
        goButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        designComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                designComboBoxItemStateChanged(evt);
            }
        });

        ruleComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ruleComboBoxItemStateChanged(evt);
            }
        });

        jLabel2.setText("Iterations:");

        iterationsLabel.setText("0");

        iterationTextBox.setText("0");

        jLabel3.setText("Completed:");

        jLabel5.setText("Speed:");

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        recallButton.setText("Load");
        recallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recallButtonActionPerformed(evt);
            }
        });

        resetButton.setText("RESET");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        s2.setText("2");
        s2.setEnabled(false);

        s1.setText("1");
        s1.setEnabled(false);

        s8.setText("8");
        s8.setEnabled(false);

        s3.setText("3");
        s3.setEnabled(false);

        s4.setText("4");
        s4.setEnabled(false);

        s5.setText("5");
        s5.setEnabled(false);

        s6.setText("6");
        s6.setEnabled(false);

        s7.setText("7");
        s7.setEnabled(false);

        b8.setText("8");
        b8.setEnabled(false);

        b7.setText("7");
        b7.setEnabled(false);

        b6.setText("6");
        b6.setEnabled(false);

        b5.setText("5");
        b5.setEnabled(false);

        b4.setText("4");
        b4.setEnabled(false);

        b3.setText("3");
        b3.setEnabled(false);

        b2.setText("2");
        b2.setEnabled(false);

        b1.setText("1");
        b1.setEnabled(false);

        survivesLabel.setText("Survives");
        survivesLabel.setEnabled(false);

        bornLabel.setText("Born");
        bornLabel.setEnabled(false);

        s0.setText("0");
        s0.setEnabled(false);

        b0.setText("0");
        b0.setEnabled(false);

        jLabel6.setText("Heuristic Value:");

        heuristicLabel.setText("0.0");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(designComboBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                            .add(layout.createSequentialGroup()
                                .add(speedComboBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                            .add(layout.createSequentialGroup()
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(iterationTextBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, resetButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(jLabel5)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                            .add(layout.createSequentialGroup()
                                .add(ruleComboBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                            .add(layout.createSequentialGroup()
                                .add(jLabel1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                            .add(layout.createSequentialGroup()
                                .add(jLabel4)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(goButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                    .add(stopButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                    .add(layout.createSequentialGroup()
                                        .add(saveButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 91, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 19, Short.MAX_VALUE)
                                        .add(recallButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 89, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                        .add(layout.createSequentialGroup()
                            .add(jLabel3)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(iterationsLabel)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                    .add(layout.createSequentialGroup()
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(heuristicLabel)
                        .add(47, 47, 47)))
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(33, 33, 33)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(survivesLabel)
                            .add(bornLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(s0)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(s1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(s2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(s3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(s4)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(s5)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(s6)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(s7)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(s8))
                            .add(layout.createSequentialGroup()
                                .add(b0)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(b1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(b2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(b3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(b4)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(b5)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(b6)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(b7)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(b8))))
                    .add(layout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(boardPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(designComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ruleComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(speedComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(resetButton)
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(iterationTextBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel3)
                            .add(iterationsLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel6)
                            .add(heuristicLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 21, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(saveButton)
                            .add(recallButton))
                        .add(37, 37, 37))
                    .add(boardPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(s0)
                            .add(survivesLabel)
                            .add(s1)
                            .add(s2)
                            .add(s3)
                            .add(s4)
                            .add(s5)
                            .add(s6)
                            .add(s7)
                            .add(s8))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(b0)
                            .add(b1)
                            .add(b2)
                            .add(b3)
                            .add(b4)
                            .add(b5)
                            .add(b6)
                            .add(b7)
                            .add(b8)
                            .add(bornLabel)))
                    .add(stopButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(375, Short.MAX_VALUE)
                .add(goButton)
                .add(70, 70, 70))
        );

        pack();
    }// </editor-fold>

    private void goButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        goButtonPress();
    }                                        

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        stopButtonPress();
    }                                          

    private void designComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {                                                
        designSelection();
    }                                               

    private void recallButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        recallButtonPressed();
    }                                            

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        saveButtonPressed();
    }                                          

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        resetButtonPressed();
    }                                           

    private void ruleComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {                                              
        ruleSelection();
    }                                             

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JCheckBox b0;
    private javax.swing.JCheckBox b1;
    private javax.swing.JCheckBox b2;
    private javax.swing.JCheckBox b3;
    private javax.swing.JCheckBox b4;
    private javax.swing.JCheckBox b5;
    private javax.swing.JCheckBox b6;
    private javax.swing.JCheckBox b7;
    private javax.swing.JCheckBox b8;
    private javax.swing.JPanel boardPanel;
    private javax.swing.JLabel bornLabel;
    private java.awt.Choice designComboBox;
    private javax.swing.JButton goButton;
    private javax.swing.JLabel heuristicLabel;
    private javax.swing.JTextField iterationTextBox;
    private javax.swing.JLabel iterationsLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JButton recallButton;
    private javax.swing.JButton resetButton;
    private java.awt.Choice ruleComboBox;
    private javax.swing.JCheckBox s0;
    private javax.swing.JCheckBox s1;
    private javax.swing.JCheckBox s2;
    private javax.swing.JCheckBox s3;
    private javax.swing.JCheckBox s4;
    private javax.swing.JCheckBox s5;
    private javax.swing.JCheckBox s6;
    private javax.swing.JCheckBox s7;
    private javax.swing.JCheckBox s8;
    private javax.swing.JButton saveButton;
    private java.awt.Choice speedComboBox;
    private javax.swing.JButton stopButton;
    private javax.swing.JLabel survivesLabel;
    // End of variables declaration

}
