package com.buzznet.collywoodtv;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class CpfileUtil {

	private static final long MAX_SIZE = 5242880L;
			    public static void CopyStream(InputStream is, OutputStream os)
			    {
			        final int buffer_size=1024;
			        long size = getDirSize(FileCache.getCacheDir());
					
                 
			        
			        
			        try
			        {
			        	//ByteArrayOutputStream baos = new ByteArrayOutputStream(os);
			        	//baos.
			            byte[] bytes=new byte[buffer_size];
			            for(;;)
			            {
			              int count=is.read(bytes, 0, buffer_size);
			              if(count==-1)
			                  break;
			              long newSize = bytes.length + size;
			              if (size > MAX_SIZE) {
					            FileCache.clear();
					        }
			              os.write(bytes, 0, count);
			            }
			        }
			        catch(Exception ex){}
			    }
			    
				
		        private static long getDirSize(File dir) {

		            long size = 0;
		            File[] files = dir.listFiles();

		            for (File file : files) {
		                if (file.isFile()) {
		                    size += file.length();
		                }
		            }

		            return size;
		        }
		
	
}
