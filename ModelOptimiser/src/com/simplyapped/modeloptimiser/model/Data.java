package com.simplyapped.modeloptimiser.model;

import java.util.ArrayList;
import java.util.List;

public class Data
{
	private String sourceFolder;
	private String targetFolder;
	private String fbxFile;
	private boolean isComplete;
	private List<String> errors = new ArrayList<String>();
	
	public String getSourceFolder()
	{
		return sourceFolder;
	}
	public void setSourceFolder(String sourceFolder)
	{
		this.sourceFolder = sourceFolder;
	}
	public String getTargetFolder()
	{
		return targetFolder;
	}
	public void setTargetFolder(String targetFolder)
	{
		this.targetFolder = targetFolder;
	}
	public String getFbxFile()
	{
		return fbxFile;
	}
	public void setFbxFile(String fbxFile)
	{
		this.fbxFile = fbxFile;
	}
	public boolean isComplete()
	{
		return isComplete;
	}
	public void setComplete(boolean isComplete)
	{
		this.isComplete = isComplete;
	}
	public List<String> getErrors()
	{
		return errors;
	}
	public void setErrors(List<String> errors)
	{
		this.errors = errors;
	}
	public void addError(String error)
	{
		this.errors.add(error);	
	}
}
