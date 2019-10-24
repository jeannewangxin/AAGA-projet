package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultTeam {
  public ArrayList<Point> calculConnectedDominatingSet(ArrayList<Point> points, int edgeThreshold) {
    //REMOVE >>>>>
    ArrayList<Point> result = (ArrayList<Point>)points.clone();
    for (int i=0;i<points.size()/3;i++) result.remove(0);
    // if (false) result = readFromFile("output0.points");
    // else saveToFile("output",result);
    //<<<<< REMOVE

    return result;
  }
  
  //FILE PRINTER
  private void saveToFile(String filename,ArrayList<Point> result){
    int index=0;
    try {
      while(true){
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename+Integer.toString(index)+".points")));
        try {
          input.close();
        } catch (IOException e) {
          System.err.println("I/O exception: unable to close "+filename+Integer.toString(index)+".points");
        }
        index++;
      }
    } catch (FileNotFoundException e) {
      printToFile(filename+Integer.toString(index)+".points",result);
    }
  }
  private void printToFile(String filename,ArrayList<Point> points){
    try {
      PrintStream output = new PrintStream(new FileOutputStream(filename));
      int x,y;
      for (Point p:points) output.println(Integer.toString((int)p.getX())+" "+Integer.toString((int)p.getY()));
      output.close();
    } catch (FileNotFoundException e) {
      System.err.println("I/O exception: unable to create "+filename);
    }
  }

  //FILE LOADER
  private ArrayList<Point> readFromFile(String filename) {
    String line;
    String[] coordinates;
    ArrayList<Point> points=new ArrayList<Point>();
    try {
      BufferedReader input = new BufferedReader(
          new InputStreamReader(new FileInputStream(filename))
          );
      try {
        while ((line=input.readLine())!=null) {
          coordinates=line.split("\\s+");
          points.add(new Point(Integer.parseInt(coordinates[0]),
                Integer.parseInt(coordinates[1])));
        }
      } catch (IOException e) {
        System.err.println("Exception: interrupted I/O.");
      } finally {
        try {
          input.close();
        } catch (IOException e) {
          System.err.println("I/O exception: unable to close "+filename);
        }
      }
    } catch (FileNotFoundException e) {
      System.err.println("Input file not found.");
    }
    return points;
  }
  
  
  //create 100 points different
  private ArrayList<Point> createPoints(){
	  ArrayList<Point> points = new ArrayList<Point>();
		long seedx = System.currentTimeMillis();
		long seedy = System.currentTimeMillis();
		//apply formula to seed for each new number
		int i = 0;
		String strx = getBinary(seedx);	
		String stry = getBinary(seedy);	
		int x = 0,y = 0;
		while(i<100) {
			if(strx.length()-10>0) {
				 x = Integer.parseInt(strx.substring(0, 9),2);
				 seedx = x;
				 strx = strx.substring(10,strx.length());
				 
			}else {
				
				strx = getBinary(seedx);
				x = Integer.parseInt(strx.substring(0, 9),2);
				seedx = x;
				strx = strx.substring(10,strx.length());
			}
			if(stry.length()-9>0) {
				y = Integer.parseInt(stry.substring(0, 8),2);
				seedy = y;
				stry = stry.substring(9,stry.length());
			}else {
				stry = getBinary(seedy);	
				y = Integer.parseInt(stry.substring(0, 8),2);
				seedy = y;
				stry = stry.substring(9,stry.length());
			}
			
			Point p = new Point();
			p.x = x;
			p.y = y;
			points.add(p);
			ArrayList<Point> origin = removeDuplicates(points);
			i = origin.size();
			
		}

	  return points;
  }
  
  private String getBinary(long graine) {
	  
		int a = 1664525;
		int c = 1013904223;
		long m =(long) Math.pow(2, 32);
		graine = ((a*graine)+c) % m;
		String str = Long.toBinaryString(graine);	
	  
	  return str;
  }
  
  private boolean isValid(ArrayList<Point> candidates, ArrayList<Point> pointsIn, int edgeThreshold) {
		ArrayList<Point> points = (ArrayList<Point>) pointsIn.clone();
		points.removeAll(candidates);
		for (Point p : candidates) {
			ArrayList<Point> neighbors = getNeighbors(p, points, edgeThreshold);
			points.removeAll(neighbors);
		}
		if (points.size() == 0)
			return true;
		return false;
	}
  
	private ArrayList<Point> getNeighbors(Point p, ArrayList<Point> vertices, int edgeThreshold) {
		ArrayList<Point> result = new ArrayList<Point>();
		for (Point point : vertices)
			if (point.distance(p) < edgeThreshold && !point.equals(p))
				result.add((Point) point.clone());
		return result;
	}
	
	
	private ArrayList<Point> greedy(ArrayList<Point> pointsIn, int edgeThreshold) {
		ArrayList<Point> domSet = new ArrayList<Point>();//用于存放domset结果
		ArrayList<Point> origin = (ArrayList<Point>) pointsIn.clone();
		origin.removeAll(getPointSolide(pointsIn,edgeThreshold));//用于存放所有连接的点
		Point p = origin.get(0);//获得一个点来进行查找
		ArrayList<Point> dom = getNeighbors(p,pointsIn,edgeThreshold);//从第一个点的邻居开始确定下一个点
		ArrayList<Point> rest = getNeighbors(p,pointsIn,edgeThreshold);//用于存放已经连接了几个点
		int i=0;
		while(removeDuplicates(rest).size()<origin.size()) {
			Point point = dom.get(i);
			rest.addAll(getNeighbors(point,pointsIn,edgeThreshold));
			dom = getNeighbors(point,pointsIn,edgeThreshold);
			System.out.println(removeDuplicates(rest).size()+"/"+origin.size());
			i++;
			if(i>=dom.size()) {
				i=0;
			}
		}
		return domSet;
	}
	
	private ArrayList<Point> removeDuplicates(ArrayList<Point> points) {
		ArrayList<Point> result = (ArrayList<Point>) points.clone();
		for (int i = 0; i < result.size(); i++) {
			for (int j = i + 1; j < result.size(); j++)
				if (result.get(i).equals(result.get(j))) {
					result.remove(j);
					j--;
				}
		}
		return result;
	}

	private int degree(Point p, ArrayList<Point> points, int edgeThreshold) {
		int degree = -1;
		for (Point q : points)
			if (isEdge(p, q, edgeThreshold))
				degree++;
		return degree;
	}
	
	private boolean isEdge(Point p, Point q, int edgeThreshold) {
		return p.distance(q) < edgeThreshold;
	}
	
	private ArrayList<Point> getPointSolide(ArrayList<Point> pointsIn, int edgeThreshold) {
		ArrayList<Point> result = new ArrayList<Point>();
		for (Point p : pointsIn) {
			if (degree(p, pointsIn, edgeThreshold) == 0) {
				result.add((Point) p.clone());
			}
		}
		return result;
	}
	
	public static void main(String arg[]) {
		DefaultTeam defaultTeam = new DefaultTeam();
		ArrayList<Point> points = defaultTeam.createPoints();
//		//connected set先把单独的给去掉
//		ArrayList<Point> solide = defaultTeam.getPointSolide(points,100);
//		ArrayList<Point> res = defaultTeam.greedy(points,100);
		int i=1;
		for(Point pp:points) {
			System.out.println(i+"x="+pp.x+","+"y="+pp.y);
			i++;
		}

	}
	
	
}
