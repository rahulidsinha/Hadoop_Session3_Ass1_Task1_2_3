import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Test_listDirectories {

	public static void main(String[] args) throws IOException {
		
		Configuration conf = new Configuration();
		
		// adding the core-site.xml and hdfs-site.xml in Configuration
		conf.addResource(new Path("/usr/local/hadoop/etc/hadoop/core-site.xml"));
		conf.addResource(new Path("/usr/local/hadoop/etc/hadoop/hdfs-site.xml"));
		
		FileSystem fs = FileSystem.get(conf);
		
		// Creating a Path[] and putting all the input paths
		// given in command line argument in different index.
		Path[] input_path = new Path[args.length];
		
		for (int index = 0;index < args.length;index++ )
		{
			input_path[index] = new Path(args[index]);
		}
		
		
		//Test_listDirectories.listDirectories(fs,input_path);
		
		
		Test_listDirectories.listDirectoriesRecursively(fs,input_path);
	}
	
	// Function to list directories for the given path
	public static void listDirectories(FileSystem fs,Path path[]) throws FileNotFoundException, IOException
	{
		// Loop to list directories for all the paths from command line argument
		for (int index = 0; index < path.length;index++)
		{
			
			FileStatus[] status = fs.listStatus(path[index]);
			
			// Iterate over all the files and directories
			for(FileStatus file:status)
			{
				System.out.println("Name       : "+file.getPath().toString());
				System.out.println("Is File?   : "+file.isFile());
				System.out.println("Length     : "+file.getLen());
				System.out.println("Permission : "+file.getPermission().toString());
				System.out.println("*******************************");
			}
		}
	}
	
	// Function to list directories for the given path recursively
	public static void listDirectoriesRecursively(FileSystem fs,Path path[]) throws FileNotFoundException, IOException
	{
		// Loop to list directories for all the paths from command line argument
		for (int index = 0; index < path.length;index++)
		{
			FileStatus[] status = fs.listStatus(path[index]);
			
			for(FileStatus file:status)
			{
				System.out.println("Name       : "+file.getPath().toString());
				System.out.println("Is File?   : "+file.isFile());
				System.out.println("Length     : "+file.getLen());
				System.out.println("Permission : "+file.getPermission().toString());
				System.out.println("*******************************");
				
				// If it is a directory list all the sub-directories and files
				if(file.isDirectory())
				{
					Path[] temp_path = new Path[1];
					temp_path[0] = file.getPath();
					listDirectoriesRecursively(fs,temp_path);
				}
			}
		}
		
	}


}