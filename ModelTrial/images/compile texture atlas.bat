SET LIBGDX="C:\Development\Java\lib\libgdx-0.9.8"
SET SOURCE="model_trial_atlas_src"
SET BUILD="atlas_out"
SET TARGET="..\..\ModelTrial-android\assets\data"
java -cp %LIBGDX%\gdx.jar;%LIBGDX%\extensions\gdx-tools\gdx-tools.jar com.badlogic.gdx.tools.imagepacker.TexturePacker2 %SOURCE% %BUILD% modeltrial
robocopy %BUILD% %TARGET% /LOG:robocopylog.txt
pause