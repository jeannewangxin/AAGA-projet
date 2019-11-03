package algorithms;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class newPoint {
	public ArrayList<Point> calculConnectedDominatingSet(ArrayList<Point> points, int edgeThreshold) {
		ArrayList<Point> rest = (ArrayList<Point>) points.clone();
		ArrayList<Point> result = new ArrayList<Point>();
		int i = 0;
		while (result.size() < 160) {
			i++;
			ArrayList<Point> doSet = greedy(points, edgeThreshold);
			if (doSet.size() > result.size())
				result = doSet;
			System.out.println(i + "eme tour, size = " + result.size() + "isDominant?" + isDominant(points, result, 100)
					+ "-->isValideIS" + isValideIS(points, result, 100));
		}
		return result;
	}

	public Point getMaxDegreePoint(ArrayList<Point> pointsIn, int edgeThreshold) {
		if (pointsIn != null) {
			Point pm = pointsIn.get(0);
			int max = degree(pm, pointsIn, edgeThreshold);
			for (Point p : pointsIn) {
				if (degree(p, pointsIn, edgeThreshold) > max) {
					max = degree(p, pointsIn, edgeThreshold);
					pm = p;
				}
			}
			return pm;
		}
		return null;
	}

	// FILE LOADER
	private ArrayList<Point> readFromFile(String filename) {
		String line;
		String[] coordinates;
		ArrayList<Point> points = new ArrayList<Point>();
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			try {
				while ((line = input.readLine()) != null) {
					coordinates = line.split("\\s+");
					points.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
				}
			} catch (IOException e) {
				System.err.println("Exception: interrupted I/O.");
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					System.err.println("I/O exception: unable to close " + filename);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Input file not found.");
		}
		return points;
	}

	private ArrayList<Point> getNeighbors(Point p, ArrayList<Point> vertices, int edgeThreshold) {
		ArrayList<Point> result = new ArrayList<Point>();
		for (Point point : vertices) {
			if (point.distance(p) < edgeThreshold && !point.equals(p)) {
				result.add((Point) point.clone());
			}
		}
		return result;
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

	private boolean isDominant(ArrayList<Point> pointsIn, ArrayList<Point> candidates, int edgeThreshold) {
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

	public boolean isValideIS(ArrayList<Point> pointsIn, ArrayList<Point> solution, int edgeThreshold) {
		if (solution.size() < 1)
			return false;
		ArrayList<Point> reste = (ArrayList<Point>) pointsIn.clone();
		reste.removeAll(solution);
		for (Point point : solution) {
			for (Point po : solution) {
				if (point.equals(po))
					continue;
				else {
					if (po.distance(point) < edgeThreshold)
						return false;
				}
			}
		}
		for (Point p : reste) {
			if (!isConnected(p, solution, edgeThreshold)) {
				return false;
			}
		}
		return true;
	}

	public boolean isConnected(Point p, ArrayList<Point> solution, int edgeThreshold) {
		for (Point so : solution) {
			if (so.distance(p) <= edgeThreshold && !so.equals(p)) {
				return true;
			}
		}
		return false;
	}

	private ArrayList<Point> getMIS(ArrayList<Point> pointsIn, int edgeThreshold) {
		long seed = System.nanoTime();
		Collections.shuffle(pointsIn, new Random(seed));
		ArrayList<Point> candi = new ArrayList<Point>();
		ArrayList<Point> solu = new ArrayList<Point>();
		while (!isValideIS(pointsIn, solu, edgeThreshold)) {
			long chose = System.nanoTime();
			for (Point p : solu) {
				if (chose % 2 == 0) {// 选择p,删除所有的邻居
					solu.removeAll(getNeighbors(p, pointsIn, edgeThreshold));
				} else {
					solu.remove(p);
				}
			}
		}
		return solu;
	}

	private ArrayList<Point> changeOneMIS(ArrayList<Point> solution, ArrayList<Point> pointsIn, int edgeThreshold) {
		System.out.println("solution size is :" + solution.size());
		ArrayList<Point> newsol = (ArrayList<Point>) solution.clone();
		ArrayList<Point> reste = (ArrayList<Point>) pointsIn.clone();
		reste.removeAll(solution);

		for (Point p : solution) {
			ArrayList<Point> nei = getNeighbors(p, pointsIn, edgeThreshold);
			newsol.remove(p);// 增加邻居试试
			boolean add = true;
			for (Point neib : nei) {
				for (Point s : newsol) {
					if (s.distance(neib) <= edgeThreshold) {
						// System.out.println("can't add");
						add = false;
						break;
					}
				}
				if (add) {
					// System.out.println("add one ");
					newsol.add(neib);
				}
			}
			if (!add)
				newsol.add(p);
		}
		System.out.println("newsol1 size is :" + newsol.size());
		if (isValideIS(pointsIn, newsol, edgeThreshold))
			return newsol;
		else
			return solution;

//
//		while (!isValideIS(pointsIn, solu, edgeThreshold)) {
//			long chose = System.nanoTime();
//			for(Point p:solu) {
//				if(chose%2 ==0) {//选择p,删除所有的邻居
//					solu.removeAll(getNeighbors(p,pointsIn,edgeThreshold));
//				}
//				else {
//					solu.remove(p);
//				}
//			}
//		}
	}

	private ArrayList<Point> getMIS2(Point po,ArrayList<Point> pointsIn, int edgeThreshold) {
		ArrayList<Point> solu = new ArrayList<Point>();
		ArrayList<Point> reste = (ArrayList<Point>) pointsIn.clone();
		solu.add(po);
		reste.remove(po);
		int i = 0;
		ArrayList<Point> newres = (ArrayList<Point>) solu.clone();
		while (newres.size() >= solu.size() && i < 20) {
			solu = (ArrayList<Point>) newres.clone();
			;
			newres = getOnePartof(solu, pointsIn, edgeThreshold);
			System.out.println("newres size is :" + newres.size());
		}

		System.out.println("solu size is :" + solu.size());

		for (Point p : solu) {
			for (Point q : solu) {
				if (p.distance(q) < edgeThreshold && !p.equals(q)) {
					solu.remove(q);
				}
			}
		}

		System.out.println("after solu size is :" + solu.size());
		return solu;
	}

	private ArrayList<Point> getOnePartof(ArrayList<Point> solu, ArrayList<Point> pointsIn, int edgeThreshold) {
		ArrayList<Point> neibors = new ArrayList<Point>();
		ArrayList<Point> result = new ArrayList<Point>();
		for (Point p : solu) {
			// 取所有solu的邻居
			neibors.addAll(getNeighbors(p, pointsIn, edgeThreshold));
			ArrayList<Point> neneibors = new ArrayList<Point>();
			// 取所有solu邻居的邻居
			for (Point n : neibors) {
				neneibors.addAll(getNeighbors(n, pointsIn, edgeThreshold));
				ArrayList<Point> res = getNeiborsInconnected(neneibors, edgeThreshold);
				ArrayList<Point> newres = (ArrayList<Point>) res.clone();
				int i = 0;
				while (newres.size() >= res.size() && i < 20) {
					res = (ArrayList<Point>) newres.clone();
					newres = getNeiborsInconnected(neneibors, edgeThreshold);
				}
				result.addAll(newres);
			}
		}
		System.out.println("getOnePartof solu size is :" + result.size());
		return result;

	}

	private ArrayList<Point> getNeiborsInconnected(ArrayList<Point> points, int edgeThreshold) {
		ArrayList<Point> res = new ArrayList<Point>();
		long seed = System.nanoTime();
		Collections.shuffle(points, new Random(seed));
		for (Point p : points) {
			if (isConnected(p, points, edgeThreshold))
				break;
			else
				res.add(p);
		}
		int max = 0;
		do {
			max = res.indexOf(getMaxDegreePoint(res, edgeThreshold));
			if (max > 0) {
				res.remove(max);
			}
		} while (max > 0);
		return res;
	}

	private ArrayList<Point> greedy(ArrayList<Point> pointsIn, int edgeThreshold) {
		long seed = System.nanoTime();
		Collections.shuffle(pointsIn, new Random(seed));
		ArrayList<Point> solu = new ArrayList<Point>();
		ArrayList<Point> reste = (ArrayList<Point>) pointsIn.clone();
		while (!isValideIS(pointsIn, solu, edgeThreshold)) {
			for (int i = 0; i < reste.size(); i++) {
				if (solu.isEmpty()) {
					solu.add(reste.get(i));
					reste.remove(i);
				} else {
					if (!isConnected(reste.get(i), solu, edgeThreshold)) {
						solu.add(reste.get(i));
						reste.remove(i);
					}
				}
			}
		}
		return solu;
	}

	public ArrayList<Point> getMiniDegreePoint(ArrayList<Point> pointsIn, int edgeThreshold) {
		int mini = 10000;
		ArrayList<Point> pm = new ArrayList<Point>();
		for (Point p : pointsIn) {
			if (degree(p, pointsIn, edgeThreshold) < mini) {
				pm.clear();
				mini = degree(p, pointsIn, edgeThreshold);
				pm.add(p);
			}
			if (degree(p, pointsIn, edgeThreshold) == mini) {

			}
		}
		return pm;
	}

	public static void main(String arg[]) {
		newPoint defaultTeam = new newPoint();
		ArrayList<Point> points = defaultTeam.readFromFile("./input.points");
		System.out.println("pointsBlanc size : " + points.size());

		ArrayList<Point> DS = defaultTeam.greedy(points, 100);
		System.out.println("getMIS isValideMIS : " + defaultTeam.isValideIS(points, DS, 100));
		System.out.println("getMIS isDominant : " + defaultTeam.isDominant(points, DS, 100));
		System.out.println("getMIS MIS size : " + DS.size());

		ArrayList<Point> addDS = defaultTeam.greedy(points, 100);
		int i = 0;
		while (i < 10) {
			addDS = defaultTeam.changeOneMIS(addDS, points, 100);
			i++;
		}

		System.out.println("addDS isValideMIS : " + defaultTeam.isValideIS(points, addDS, 100));
		System.out.println("addDS isDominant : " + defaultTeam.isDominant(points, addDS, 100));
		System.out.println("addDS MIS size : " + addDS.size());

		for(Point p:points) {
			ArrayList<Point> getMIS2;
			ArrayList<Point> newgetMIS2;
			do {
				getMIS2 = defaultTeam.getMIS2(p,points, 100);
				newgetMIS2 = (ArrayList<Point>)getMIS2.clone();
			}while(newgetMIS2.size()>getMIS2.size());
		}

		
		System.out.println("getMIS2 isValideMIS : " + defaultTeam.isValideIS(points, getMIS2, 100));
		System.out.println("getMIS2 isDominant : " + defaultTeam.isDominant(points, getMIS2, 100));
		System.out.println("getMIS2 MIS size : " + getMIS2.size());
	}

}
