/*Kai Kang *
*CEG7370   *
*section 02*
*project1  */  


import java.util.StringTokenizer;
import java.io.IOException;  
import java.util.Iterator;    
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;  
import org.apache.hadoop.io.Text;  
import org.apache.hadoop.mapreduce.Job;  
import org.apache.hadoop.mapreduce.Mapper;  
import org.apache.hadoop.mapreduce.Reducer;  
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;  
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;  
import org.apache.hadoop.util.GenericOptionsParser;  
  

public class connectedlink {  
    public static int time = 0;  
      
    public static class testMapper extends Mapper<Object, Text, Text, Text>{  
  
        @Override  
        protected void map(Object key, Text value, Context context)  
                throws IOException, InterruptedException {  
            String childName = new String();  
            String parentName = new String();  
            String relation = new String();  
            String line = value.toString();  
            int i =0;  
            while(line.charAt(i)!=' '){  
                i++;  
            }  
            String[] values = {line.substring(0,i),line.substring(i+1)};  
            if(values[0].compareTo("child") != 0){  
                childName = values[0];  
                parentName = values[1];  
                relation = "1";//section symble 
                context.write(new Text(parentName),new Text(relation+"+"+childName));//left
                relation = "2";  
                context.write(new Text(childName), new Text(relation+"+"+parentName));//right
            }  
        }  
    }  
      
    public static class testReduce extends Reducer<Text, Text, Text, Text>{  
  
        @Override  
        protected void reduce(Text key, Iterable<Text> values,Context context)  
                throws IOException, InterruptedException {  
            if(time ==0){ 
                context.write(new Text("grandChild"), new Text("grandParent"));  
                time ++;  
            }  
            int grandChildNum = 0;  
            String[] grandChild = new String[10];  
            int grandParentNum = 0;  
            String[] grandParent = new String[10];  
            Iterator<Text> ite = values.iterator();  
            while(ite.hasNext()){  
                String record = ite.next().toString();  
                int len = record.length();  
                int i = 2;  
                if(len ==0)  continue;  
                char relation = record.charAt(0);  
                  
                if(relation == '1'){// left side child  
                    String childName = new String();  
                    while(i < len){ 
                        childName = childName + record.charAt(i);  
                        i++;  
                    }  
                    grandChild[grandChildNum] = childName;  
                    grandChildNum++;  
                }else{//right parent  
                    String parentName = new String();  
                    while(i < len){  
                        parentName = parentName + record.charAt(i);  
                        i++;  
                    }  
                    grandParent[grandParentNum] = parentName;  
                    grandParentNum++;  
                }  
            }  
            //  
            if(grandChildNum!=0&&grandParentNum!=0){  
                for(int m=0;m<grandChildNum;m++){  
                    for(int n=0;n<grandParentNum;n++){  
                        System.out.println("child "+grandChild[m] +" grandparents "+ grandParent[n]);  
                        context.write(new Text(grandChild[m]),new Text(grandParent[n]));  
                    }  
                }  
            }  
        }  
    }  
      
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{  
        Configuration conf = new Configuration();  
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();  
        if(otherArgs.length<2){  
            System.out.println("error");  
            System.exit(2);  
        }  
          
        Job job = new Job(conf);  
        job.setJarByClass(test.class);  
        job.setMapperClass(testMapper.class);  
        job.setReducerClass(testReduce.class);  
        job.setOutputKeyClass(Text.class);  
        job.setOutputValueClass(Text.class);  
          
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));  
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));  
          
        System.exit(job.waitForCompletion(true)?0:1);  
    }  
}  
