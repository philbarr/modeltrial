package com.simplyapped.modeloptimiser.actions;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;
import com.simplyapped.modeloptimiser.AppFrame;
import com.simplyapped.modeloptimiser.model.Data;

public class OptimiseAction extends AppFrameAction
{
	final int REQUESTED_WIDTH = 256;

	public OptimiseAction(Data data, AppFrame appFrame)
	{
		super(data, appFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		File source = new File(data.getSourceFolder());
		File target = new File(data.getTargetFolder());
		File exe = new File(data.getFbxFile());
		
		if (check(source) &&
			check(target) &&
			check(exe))
		{
			String suffix = "jpg";
			String fuckedFiles = "";
			boolean succeeded = true;
			for (File file : listFiles(source.getPath(), suffix))
			{
				try
				{
					resize(file, new File(target.getPath() + "/" + file.getName()), REQUESTED_WIDTH, suffix);
				} catch (IOException e1)
				{
					fuckedFiles += file.getName() + " failed: " + e1.getMessage() + "\n";
					succeeded = false;
				}
			}
			data.addError("Error\n" + fuckedFiles);
			data.setComplete(succeeded);
		}
		else
		{
			data.setComplete(false);
		}
		
		frame.updateFields();
	}

	List<File> listFiles(String directory, String suffix) {
		  List<File> textFiles = new ArrayList<File>();
		  File dir = new File(directory);
		  for (File file : dir.listFiles()) {
		    if (file.getName().endsWith(("." + suffix))) {
		      textFiles.add(file);
		    }
		  }
		  return textFiles;
		}
	
	private boolean check(File fileOrFolder)
	{
		if (!fileOrFolder.exists())
		{
			data.addError(fileOrFolder + " does not exist");
			return false;
		}
		return true;
	}
	
	private void resize(File source, File target, int rw, String suffix) throws IOException
	{
		BufferedImage image = ImageIO.read(source);

		ResampleOp  resampleOp = new ResampleOp(rw,(rw * image.getHeight()) / image.getWidth() );
		resampleOp.setFilter(ResampleFilters.getLanczos3Filter()); 
		image = resampleOp.filter(image, null);

		ImageIO.write(image, suffix, target);
	}
}
