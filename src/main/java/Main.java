import java.io.*;

public class Main 
{
    public static void main(String[] args) 
    {
        File dir = new File(".");
        Main me  = new Main();
        
        me.scanDirectory(dir);
    }
    
    /**
     * Scan single directory and call himself for nested directories.
     * @param dir current directory
     * @return symply return anything
     */
    private boolean scanDirectory(File dir)
    {
        File[] content = dir.listFiles();
        
        for(File f: content)
        {
            if(f.isFile())
            {
                if(isJar(f.getName()))
                {
                    generateBat(dir, f.getName());
                }
            }
            
            if(f.isDirectory())
            {
                scanDirectory(f);
            }
        }
        
        return true;
    }
    
    /**
     * Checking file extension.
     * @param name typically filename
     * @return true if file have .jar extension
     */
    private boolean isJar(String name)
    {
        return name.toLowerCase().endsWith(".jar");
    }
    
    /**
     * Generate .bat file for starting specific .jar
     * @param name typically filename of .jar
     * @return true if operation success
     */
    private boolean generateBat(File dir, String name)
    {
        String new_name = name.substring(0, name.lastIndexOf(".jar"));
        new_name        = dir.getPath() + "/" + new_name + ".bat";
                 
        try(BufferedWriter out = new BufferedWriter(new FileWriter(new_name)))
        {
            out.write("java -jar " + name);
            out.newLine();
            out.write("pause");
            
            return true;
        }
        catch(IOException e)
        {
            return false;
        }
    }
}
