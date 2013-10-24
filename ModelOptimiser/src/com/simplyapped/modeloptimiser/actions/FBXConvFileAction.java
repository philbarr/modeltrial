package com.simplyapped.modeloptimiser.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.simplyapped.modeloptimiser.AppFrame;
import com.simplyapped.modeloptimiser.model.Data;

public class FBXConvFileAction extends AppFrameAction
{
	public FBXConvFileAction(Data data, AppFrame appFrame)
	{
		super(data, appFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Exe Files", "exe");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	            String file = chooser.getSelectedFile().getName();
	            data.setFbxFile(file);
	            frame.updateFields();
	    }
	}
}
