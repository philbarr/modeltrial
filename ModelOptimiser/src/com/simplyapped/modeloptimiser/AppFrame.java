package com.simplyapped.modeloptimiser;

import java.awt.EventQueue;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.simplyapped.modeloptimiser.actions.FBXConvFileAction;
import com.simplyapped.modeloptimiser.actions.OpenFolderAction;
import com.simplyapped.modeloptimiser.actions.OptimiseAction;
import com.simplyapped.modeloptimiser.actions.SourceFolderAction;
import com.simplyapped.modeloptimiser.actions.TargetFolderAction;
import com.simplyapped.modeloptimiser.model.Data;

public class AppFrame extends JFrame
{
	private JPanel contentPane;
	private JTextField txtSource;
	private JTextField txtTarget;
	private Data data;
	private final OpenFolderAction sourceAction;
	private final OpenFolderAction targetAction;
	private final OptimiseAction optimiseAction;
	private final FBXConvFileAction fbxAction;
	private JTextField textFBXConv;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					AppFrame frame = new AppFrame();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AppFrame()
	{
		data = new Data();
		sourceAction = new SourceFolderAction(data, this);
		targetAction = new TargetFolderAction(data, this);
		optimiseAction = new OptimiseAction(data, this);
		fbxAction = new FBXConvFileAction(data, this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 188);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtSource = new JTextField();
		txtSource.setBounds(73, 46, 294, 24);
		contentPane.add(txtSource);
		txtSource.setColumns(10);
		
		JButton btnSource = new JButton("...");
		btnSource.setAction(sourceAction);
		btnSource.setBounds(377, 46, 47, 24);
		contentPane.add(btnSource);
		
		JButton btnTarget = new JButton("...");
		btnTarget.setAction(targetAction);
		btnTarget.setBounds(377, 81, 47, 24);
		contentPane.add(btnTarget);
		
		txtTarget = new JTextField();
		txtTarget.setColumns(10);
		txtTarget.setBounds(73, 81, 294, 24);
		contentPane.add(txtTarget);
		
		JLabel lblSource = new JLabel("Source:");
		lblSource.setBounds(10, 51, 46, 14);
		contentPane.add(lblSource);
		
		JLabel lblOutput = new JLabel("Output:");
		lblOutput.setBounds(10, 86, 46, 14);
		contentPane.add(lblOutput);
		
		JButton btnDoIt = new JButton("Do It");
		btnDoIt.setAction(optimiseAction);
		btnDoIt.setBounds(176, 116, 89, 23);
		contentPane.add(btnDoIt);
		
		textFBXConv = new JTextField();
		textFBXConv.setColumns(10);
		textFBXConv.setBounds(73, 11, 294, 24);
		contentPane.add(textFBXConv);
		
		JButton btnFBX = new JButton("...");
		btnFBX.setBounds(377, 11, 47, 24);
		contentPane.add(btnFBX);
		
		JLabel lblFbxConv = new JLabel("FBX Conv:");
		lblFbxConv.setBounds(10, 16, 53, 14);
		contentPane.add(lblFbxConv);
		

		
	}

	public void updateFields()
	{
		txtSource.setText(data.getSourceFolder() == null ? "" : data.getSourceFolder());
		txtTarget.setText(data.getTargetFolder() == null ? "" : data.getTargetFolder());
		textFBXConv.setText(data.getFbxFile() == null ? "" : data.getFbxFile());
		if (data.isComplete())
		{
			JOptionPane.showMessageDialog(this, "Optimisation Complete!");
		}
		else if (data.getErrors().size()>0)
		{
			for (String error : data.getErrors())
			{
				JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);				
			}
			data.getErrors().clear();
		}
	}
}
