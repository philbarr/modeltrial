package com.simplyapped.modeloptimiser.actions;

import com.simplyapped.modeloptimiser.AppFrame;
import com.simplyapped.modeloptimiser.model.Data;

public class SourceFolderAction extends OpenFolderAction
{

	public SourceFolderAction(Data data, AppFrame appFrame)
	{
		super(data, appFrame);
	}

	@Override
	protected void assignFolderToData(String folder)
	{
		data.setSourceFolder(folder);
	}

}
