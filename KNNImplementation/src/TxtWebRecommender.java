import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.neighboursearch.LinearNNSearch;


public class TxtWebRecommender {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//Instances insts = getInstancesFromFile("bank-data.csv");
			CSVLoader loader = new CSVLoader();
			Instances insts = null;
			loader.setSource(new File("KeywordName_Matrix.csv"));
		    insts = loader.getDataSet();
			LinearNNSearch knn = new LinearNNSearch(insts);
			
			CSVLoader targetLoader = new CSVLoader();
			targetLoader.setSource(new File("Target.csv"));
			Instances targets = targetLoader.getDataSet();
			
			Instance target = new Instance(targets.firstInstance());
			target.setDataset(insts);
			Instances nearestNbrs = knn.kNearestNeighbours(target, 5);
			System.out.println("Target User Vector:\n"+target.toString());
			//Instance temp=nearestNbrs.firstInstance();
			System.out.println("\nNeighbours:");
			File neighbours=new File("Neighbours.csv");
			neighbours.delete();
			neighbours.createNewFile();
			FileOutputStream newfos=new FileOutputStream(neighbours, true);
			newfos.write("applist,cri,cric,cricbuzz,cricguru,crick,cricke,cricket,GETX,goog,grt,jntuk,joke,jokes,lovestory,message,monitor,news,omegle,score,scr,smsbox,smsfun,t20,TA2012011107,triv,VTU,vturesult,wikipedia,word\n".getBytes());
			double[][] vals=new double[nearestNbrs.numInstances()][30];
				//System.out.print(target.attribute(e));
			for(int i=0;i<nearestNbrs.numInstances();i++)
			{
				Instance temp=nearestNbrs.instance(i);
				vals[i]=temp.toDoubleArray();
				
				newfos.write((temp.toString()).getBytes());
				newfos.write("\n".getBytes());
				System.out.println(temp.toString());

			}
			newfos.close();
			double targetArray[]=target.toDoubleArray();
			double sums[]=new double[30];
			for (int j=0;j<30;j++)
			{
				if (targetArray[j]==0)
				{
					for(int k=0;k<nearestNbrs.numInstances();k++)
					{
						sums[j]+=vals[k][j];
					}
				}
				else sums[j]=0;
			}
			
			int maxIndex=0;
			System.out.println("\nRecommendations:");
			for (int l=0;l<3;l++)
			{
				double max=0;
				for(int m=0; m<30; m++)
				{
					if (sums[maxIndex]<sums[m])
						maxIndex=m;
				}
				Attribute att = target.attribute(maxIndex);
				System.out.println(att.name());
				sums[maxIndex]=0;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}
