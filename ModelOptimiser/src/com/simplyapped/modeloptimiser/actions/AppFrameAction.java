package com.simplyapped.modeloptimiser.actions;

import javax.swing.AbstractAction;

import com.simplyapped.modeloptimiser.AppFrame;
import com.simplyapped.modeloptimiser.model.Data;


public abstract class AppFrameAction extends AbstractAction
{
	protected Data data;
	protected AppFrame frame;
	
	public AppFrameAction()
	{
	}
	
	public AppFrameAction(Data data, AppFrame frame)
	{
		this.data = data;
		this.frame = frame;
	}
}
