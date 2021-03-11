::set output directory to the current directory
SET output_directory=%cd%
SET lines=1000000
SET fields=7
cmd /k java -jar -server csv-gen-1.0.0-SNAPSHOT.jar %lines% %fields% %output_directory%