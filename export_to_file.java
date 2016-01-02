import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFrame;
import java.io.File;
class export_to_file
{
    int i=0;
    File getData(String name[],String ext[],String str,JFrame frame)
    {
        try
        {
            int ret=JOptionPane.NO_OPTION;
            customJFileChooser exkey=new customJFileChooser(System.getProperty("user.home")+"\\Desktop");
            exkey.setAcceptAllFileFilterUsed(false);
            doFilter filter[]=new doFilter[ext.length];
            for(i=0;i<name.length;i++)
            {
                filter[i]=new doFilter(name[i],ext[i]);
                exkey.addChoosableFileFilter(filter[i]);
            }
            while(ret==JOptionPane.NO_OPTION)
            {
                ret=exkey.showSaveDialog(frame);
                if(ret==customJFileChooser.APPROVE_OPTION)
                {
                    File ff=exkey.getFile();
                    if(ff.exists())
                    {
                        ret=JOptionPane.showConfirmDialog(frame,"Do you want to overwrite the Existing file?");
                        if(ret==JOptionPane.YES_OPTION)
                        {
                            if(str==null)
                                return ff;
                            else
                                encode_gui.write_to_file(ff,str);
                            break;
                        }
                        else if(ret==JOptionPane.NO_OPTION)
                        {
                            continue;
                        }
                        else
                        {break;}
                    }
                    if(str==null)
                        return ff;
                    else
                        encode_gui.write_to_file(ff,str);
                }
                break;
            }
            return null;
        }
        catch(Exception ee)
        {
            JOptionPane.showMessageDialog(frame,ee);return null;
        }
    }
}
class import_from_file
{
    static File getData(String name[],String ext[],JFrame frame)
    {
        try
        {
            customJFileChooser imp=new customJFileChooser(System.getProperty("user.home")+"\\Desktop");
            imp.setAcceptAllFileFilterUsed(false);
            doFilter filter[]=new doFilter[ext.length];
            for(int i=0;i<name.length;i++)
            {
                filter[i]=new doFilter(name[i],ext[i]);
                imp.addChoosableFileFilter(filter[i]);
            }
            while(true)
            {
                int ret=imp.showOpenDialog(frame);
                if(ret==JFileChooser.APPROVE_OPTION)
                {
                    //importt.setText(imp.getSelectedFile().getAbsolutePath());
                    File ff=imp.getFile();
                    //if(!ff.getName().toLowerCase().endsWith(ext))
                        //ff=new File(ff.getAbsolutePath()+ext);
                    if(!ff.exists())
                    {
                        JOptionPane.showMessageDialog(frame,"File does not exists");
                        continue;
                    }
                    return imp.getFile();
                }
                return null;
            }
        }
        catch(Exception ee)
        {
            JOptionPane.showMessageDialog(frame,ee);
        }
        return null;
    }
}
class doFilter extends FileFilter
{
    String name,ext;
    doFilter(String name,String ext)
    {
        this.name=name;this.ext=ext;
    }
    public String getDescription()
    {
        return name+" (*"+ext+")";
    }
    public String getExtension()
    {
        return ext;
    }
    public boolean accept(File g)
    {
        if(g.isDirectory())
            return true;
        else
            return g.getName().toLowerCase().endsWith(ext);
    }
}
class customJFileChooser extends JFileChooser
{
    String ext="";
    customJFileChooser(String str)
    {
        super(str);
    }
    public void approveSelection()
    {
        doFilter temp=(doFilter)getFileFilter();
        String extt=temp.ext;
        File ff=getFile();
        if(!ff.getName().toLowerCase().endsWith(extt))
            {ext=extt;}
        super.approveSelection();
    }
    public File getFile()
    {
        File temp=getSelectedFile();
        temp=new File(temp.getAbsolutePath()+ext);
        return temp;
    }
}
