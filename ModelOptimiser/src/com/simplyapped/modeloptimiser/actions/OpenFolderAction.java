package com.simplyapped.modeloptimiser.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;

import com.simplyapped.modeloptimiser.AppFrame;
import com.simplyapped.modeloptimiser.model.Data;

public abstract class OpenFolderAction extends AppFrameAction
{	
	public OpenFolderAction(Data data, AppFrame appFrame)
	{
		super(data, appFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser chooser = new JFileChooser();
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	            String folder = chooser.getSelectedFile().getName();
	            assignFolderToData(folder);
	            frame.updateFields();
	    }
	 
	}

	protected abstract void assignFolderToData(String folder);

}
