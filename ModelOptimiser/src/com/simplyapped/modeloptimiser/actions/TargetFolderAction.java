package com.simplyapped.modeloptimiser.actions;

import com.simplyapped.modeloptimiser.AppFrame;
import com.simplyapped.modeloptimiser.model.Data;

public class TargetFolderAction extends OpenFolderAction
{

	public TargetFolderAction(Data data, AppFrame appFrame)
	{
		super(data, appFrame);
	}

	@Override
	protected void assignFolderToData(String folder)
	{
		data.setTargetFolder(folder);
	}

}
