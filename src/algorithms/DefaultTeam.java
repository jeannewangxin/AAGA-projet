//package algorithms;
//
//import java.awt.Point;
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//public class DefaultTeam {
//	public ArrayList<Point> calculConnectedDominatingSet(ArrayList<Point> points, int edgeThreshold) {
////		// REMOVE >>>>>
////		ArrayList<Point> result = (ArrayList<Point>) points.clone();
////		for (int i = 0; i < points.size() / 3; i++)
////			result.remove(0);
////		// if (false) result = readFromFile("output0.points");
////		// else saveToFile("output",result);
////		// <<<<< REMOVE
//
//		ArrayList<Point> rest = (ArrayList<Point>) points.clone();
//		ArrayList<Point> result = new ArrayList<Point>();
//		int i = 0;
//		while (i < 1000 && result.size() < 180) {
//			i++;
//			ArrayList<Point> doSet = greedy(points, edgeThreshold);
//			System.out.println(
//					"MAIN. " + i + " times greedy Current sol: " + result.size() + ". Found next sol: " + doSet.size());
//			if (doSet.size() > result.size())
//				result = doSet;
//		}
//		return result;
//	}
//
//	// FILE LOADER
//	private ArrayList<Point> readFromFile(String filename) {
//		String line;
//		String[] coordinates;
//		ArrayList<Point> points = new ArrayList<Point>();
//		try {
//			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
//			try {
//				while ((line = input.readLine()) != null) {
//					coordinates = line.split("\\s+");
//					points.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
//				}
//			} catch (IOException e) {
//				System.err.println("Exception: interrupted I/O.");
//			} finally {
//				try {
//					input.close();
//				} catch (IOException e) {
//					System.err.println("I/O exception: unable to close " + filename);
//				}
//			}
//		} catch (FileNotFoundException e) {
//			System.err.println("Input file not found.");
//		}
//		return points;
//	}
//
//	private ArrayList<Point> getNeighbors(Point p, ArrayList<Point> vertices, int edgeThreshold) {
//		ArrayList<Point> result = new ArrayList<Point>();
//		for (Point point : vertices) {
//			if (point.distance(p) < edgeThreshold && !point.equals(p)) {
//				result.add((Point) point.clone());
//			}
//		}
//		return result;
//	}
//
//	private ArrayList<Point> removeDuplicates(ArrayList<Point> points) {
//		ArrayList<Point> result = (ArrayList<Point>) points.clone();
//		for (int i = 0; i < result.size(); i++) {
//			for (int j = i + 1; j < result.size(); j++)
//				if (result.get(i).equals(result.get(j))) {
//					result.remove(j);
//					j--;
//				}
//		}
//		return result;
//	}
//
//	private int degree(Point p, ArrayList<Point> points, int edgeThreshold) {
//		int degree = -1;
//		for (Point q : points)
//			if (isEdge(p, q, edgeThreshold))
//				degree++;
//		return degree;
//	}
//
//	private boolean isEdge(Point p, Point q, int edgeThreshold) {
//		return p.distance(q) < edgeThreshold;
//	}
//
//	private boolean isDominant(ArrayList<Point> pointsIn, ArrayList<Point> candidates, int edgeThreshold) {
//		ArrayList<Point> points = (ArrayList<Point>) pointsIn.clone();
//		points.removeAll(candidates);
//		for (Point p : candidates) {
//			ArrayList<Point> neighbors = getNeighbors(p, points, edgeThreshold);
//			points.removeAll(neighbors);
//		}
//		if (points.size() == 0)
//			return true;
//		return false;
//	}
//
//	public boolean isValideMIS(ArrayList<Point> pointsIn, ArrayList<Point> solution, int edgeThreshold) {
//		if (solution.size() < 1)
//			return false;
//		ArrayList<Point> reste = (ArrayList<Point>) pointsIn.clone();
//		reste.removeAll(solution);
//		for (Point point : solution) {
//			for (Point po : solution) {
//				if (point.equals(po))
//					continue;
//				else {
//					if (po.distance(point) < edgeThreshold)
//						return false;
//				}
//			}
//		}
//		for (Point p : reste) {
//			if (!isConnected(p, solution, edgeThreshold)) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	public boolean isConnected(Point p, ArrayList<Point> solution, int edgeThreshold) {
//		for (Point so : solution) {
//			if (so.distance(p) <= edgeThreshold && !so.equals(p)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	// shali
//	public ArrayList<Point> calculIndepedantEnsemble(ArrayList<Point> points, int edgeThreshold) {
//		ArrayList<Point> result = new ArrayList<Point>();
//		ArrayList<Point> rest = (ArrayList<Point>) points.clone();
//		for (int i = 0; i < rest.size(); i++) {
//			if (!isConnected(rest.get(i), result, edgeThreshold)) {
//				result.add((Point) rest.get(i).clone());
//			}
//		}
//		return result;
//	}
//
//	private ArrayList<Point> remove1add2(ArrayList<Point> candidate, ArrayList<Point> points, int edgeThreshold) {
//		ArrayList<Point> solu = (ArrayList<Point>) candidate.clone();
//		ArrayList<Point> rest = (ArrayList<Point>) points.clone();
//		rest.removeAll(solu);
//		for (Point p : candidate) {
//			// System.out.println(candidate.indexOf(p)+"eme point");
//			// ArrayList<Point> nei = getNeighbors(p,points,edgeThreshold);
//			for (int i = 0; i < rest.size(); i++) {
//				boolean canchange = true;
//				Point un = rest.get(i);
//				solu.remove(p);// 去掉一个点
//				for (Point so : solu) {
//					if (so.distance(un) <= edgeThreshold) {
//						canchange = false;
//						solu.add(p);// 不符合，加回来
//						break;
//					}
//				}
//				if (canchange) {
//					for (int j = i + 1; j < rest.size(); j++) {
//						Point de = rest.get(j);// 比较第二个点
//						for (Point so : solu) {
//							if (so.distance(de) <= edgeThreshold) {
//								canchange = false;
//								solu.add(p);// 不符合，加回来
//								break;
//							}
//						}
//						if (canchange) {
//							solu.add(un);// 符合加新点
//							solu.add(de);
//							rest.add(p);
//						}
//					}
//				}
//			}
//		}
//		solu = removeDuplicates(solu);
//		// System.out.println("size of solu:"+solu.size());
//		return solu;
//	}
//
//	private ArrayList<Point> greedy(ArrayList<Point> pointsIn, int edgeThreshold) {
//
//		Collections.shuffle(pointsIn);
//		ArrayList<Point> solu = new ArrayList<Point>();
//		ArrayList<Point> reste = (ArrayList<Point>) pointsIn.clone();
//		while (!isValideMIS(pointsIn, solu, edgeThreshold)) {
//			System.out.println("reste size: " + reste.size());
//			for (int i = 0; i < reste.size(); i++) {
//				if (solu.isEmpty()) {
//					solu.add(reste.get(i));
//					reste.remove(i);
//				} else {
//					if (!isConnected(reste.get(i), solu, edgeThreshold)) {
//						solu.add(reste.get(i));
//						reste.remove(i);
//					}
//				}
//			}
//		}
//		return solu;
//	}
//
//	private ArrayList<Point> getMIS(ArrayList<Point> pointsIn, int edgeThreshold) {
//		ArrayList<Point> solu = new ArrayList<Point>();
//		ArrayList<Point> nei = new ArrayList<Point>();
//		ArrayList<Point> reste = (ArrayList<Point>) pointsIn.clone();
//		while (!reste.isEmpty()) {
//			for (Point p : pointsIn) {
//				if (solu.isEmpty()) {
//					solu.add(p);
//					nei.addAll(getNeighbors(p, pointsIn, edgeThreshold));
//					reste.remove(p);
//					reste.remove(getNeighbors(p, pointsIn, edgeThreshold));
//				} else {
//					if (!isConnected(p, solu, edgeThreshold)) {
//						solu.add(p);
//						nei.addAll(getNeighbors(p, pointsIn, edgeThreshold));
//						reste.remove(p);
//						reste.remove(getNeighbors(p, pointsIn, edgeThreshold));
//					} else {
//						nei.add(p);
//						reste.remove(p);
//					}
//				}
//			}
//		}
//		return solu;
//	}
//
//	public ArrayList<Point> getMiniDegreePoint(ArrayList<Point> pointsIn, int edgeThreshold) {
//		int mini = 10000;
//		ArrayList<Point> pm = new ArrayList<Point>();
//		for (Point p : pointsIn) {
//			if (degree(p, pointsIn, edgeThreshold) < mini) {
//				pm.clear();
//				mini = degree(p, pointsIn, edgeThreshold);
//				pm.add(p);
//			}
//			if (degree(p, pointsIn, edgeThreshold) == mini) {
//
//			}
//		}
//		return pm;
//	}
//
//	public Point getMaxDegreePoint(ArrayList<Point> pointsIn, int edgeThreshold) {
//		Point pm = pointsIn.get(0);
//		int max = degree(pm, pointsIn, edgeThreshold);
//		for (Point p : pointsIn) {
//			if (degree(p, pointsIn, edgeThreshold) > max) {
//				max = degree(p, pointsIn, edgeThreshold);
//				pm = p;
//			}
//		}
//		return pm;
//	}
//
//	private ArrayList<Point> getMISbyGreedy(ArrayList<Point> pointsIn, int edgeThreshold) {
//		ArrayList<Point> solu = new ArrayList<Point>();
//		ArrayList<Point> reste = (ArrayList<Point>) pointsIn.clone();
//		ArrayList<Point> newsolu = (ArrayList<Point>) solu.clone();
//		while (!reste.isEmpty()) {
//			System.out.println("reste.size()" + reste.size());
//			ArrayList<Point> mins = getMiniDegreePoint(reste, edgeThreshold);
//			int i = 0;
//			while (i < mins.size()) {
//				System.out.println("try" + i);
//				if (solu.isEmpty()) {
//					solu.add(mins.get(i));
//					reste.remove(mins.get(i));
//					reste.remove(getNeighbors(mins.get(i), pointsIn, edgeThreshold));
//				} else {
//					if (!isConnected(mins.get(i), solu, edgeThreshold)) {
//						solu.add(mins.get(i));
//						reste.remove(mins.get(i));
//						reste.remove(getNeighbors(mins.get(i), pointsIn, edgeThreshold));
//					} else {
//						reste.remove(mins.get(i));
//					}
//				}
//				i++;
//			}
//			if (solu.size() > newsolu.size())
//				newsolu = (ArrayList<Point>) solu.clone();
//		}
//		return newsolu;
//	}
//
//	private ArrayList<Point> greedyMCDS(ArrayList<Point> pointsIn, int edgeThreshold) {
//		ArrayList<Point> solu = new ArrayList<Point>();
//		ArrayList<Point> reste = (ArrayList<Point>) pointsIn.clone();
//		while (!reste.isEmpty()) {
//			Point p = getMaxDegreePoint(reste, edgeThreshold);
//			if (!isConnected(p, solu, edgeThreshold)) {
//				solu.add(p);
//				reste.remove(p);
//			} else {
//				reste.remove(p);
//			}
//			System.out.println("solu size" + solu.size());
//		}
//		return solu;
//	}
//
//	public ArrayList<Point> haveGreyNeighbors(Point p, ArrayList<Point> points, ArrayList<Point> greyPoint,
//			int edgeThreshold) {
//
//		ArrayList<Point> nei = getNeighbors(p, points, edgeThreshold);
//		ArrayList<Point> pointhave = new ArrayList<Point>();
//		for (Point point : nei) {
//			for (Point grey : greyPoint) {
//				if (point.equals(grey))
//					pointhave.add(point);
//			}
//		}
//
//		return pointhave;
//	}
//
//	public ArrayList<Point> haveNoirNeighbors(Point p, ArrayList<Point> points, ArrayList<Point> noirPoint,
//			int edgeThreshold) {
//
//		ArrayList<Point> nei = getNeighbors(p, points, edgeThreshold);
//		ArrayList<Point> pointhave = new ArrayList<Point>();
//		for (Point point : nei) {
//			for (Point noir : noirPoint) {
//				if (point.equals(noir))
//					pointhave.add(point);
//			}
//		}
//
//		return pointhave;
//	}
//
//	public ArrayList<Point> BBC(ArrayList<Point> points, int edgeThreshold) {
//
//		System.out.println("get blue points ------------------>");
//		ArrayList<Point> MISPoints = getMISbyGreedy(points, edgeThreshold);
//		ArrayList<Point> BBCPoints = new ArrayList<Point>();
//		ArrayList<Point> reste = (ArrayList<Point>) points.clone();
//		reste.removeAll(MISPoints);
//		System.out.println("reste size --->" + reste.size());
//		int i = 1;
//		int k = 1;
//		for (Point p : reste) {
//			if (changeToBlue(p, MISPoints, edgeThreshold)) {
//				BBCPoints.add(p);
//			} else {
//				if (isConnected(p, MISPoints, edgeThreshold)) {
//					System.out.println("connected < 2 ou > 5 --->" + i++);
//				} else {
//					System.out.println("not connected  --->" + k++);
//				}
//			}
//		}
//
//		return BBCPoints;
//
//	}
//
//	public ArrayList<Point> SMIS(ArrayList<Point> points, ArrayList<Point> mis, int edgeThreshold) {
//		ArrayList<Point> MISPoints = (ArrayList<Point>) mis.clone();
//		ArrayList<Point> Grey = (ArrayList<Point>) points.clone();
//		Grey.removeAll(mis);
//		Map<Integer, ArrayList<Point>> blue_black_component = new HashMap<Integer, ArrayList<Point>>();
//		int counter = 0;
//		for (Point a : mis) {
//			ArrayList<Point> r = new ArrayList<Point>();
//			r.add(a);
//			blue_black_component.put(counter, r);
//			counter++;
//		}
//
//		int i = 5;
//		while (i > 1 && blue_black_component.size() > 1) {
//			for (int j = 0; j < Grey.size(); j++) {
//				Map<Integer, ArrayList<Point>> nei = adjToBBC(blue_black_component, Grey.get(j), edgeThreshold);
//				if (nei.size() == i) {
//					MISPoints.add((Point) Grey.get(j).clone());
//					ArrayList<Point> newCom = new ArrayList<Point>();
//					for (Entry<Integer, ArrayList<Point>> e : nei.entrySet()) {
//						newCom.addAll(e.getValue());
//						blue_black_component.remove(e.getKey());
//					}
//					blue_black_component.put(counter, newCom);
//					counter++;
//				}
//			}
//			i--;
//		}
//		return MISPoints;
//	}
//
//	public boolean changeToBlue(Point p, ArrayList<Point> MIS, int edgeThreshold) {
//		int i = 0;
//		for (Point point : MIS) {
//			if (p.distance(point) <= edgeThreshold) {
//				i++;
//			}
//		}
//		if (i > 1 && i < 6) {
//			return true;
//		}
//		return false;
//	}
//
//	public Map<Integer, ArrayList<Point>> adjToBBC(Map<Integer, ArrayList<Point>> blue_black_component, Point q,
//			int edgeThreshold) {
//
//		Map<Integer, ArrayList<Point>> res = new HashMap<Integer, ArrayList<Point>>();
//		for (Map.Entry<Integer, ArrayList<Point>> e : blue_black_component.entrySet()) {
//			for (Point p : e.getValue()) {
//				if (p.distance(q) <= edgeThreshold) {
//					res.put(e.getKey(), (ArrayList<Point>) e.getValue().clone());
//					break;
//				}
//			}
//		}
//		return res;
//	}
//
//	public static void main(String arg[]) {
//		DefaultTeam defaultTeam = new DefaultTeam();
//		// noir = 0; blanc = 1; grey = 2
//		ArrayList<Point> points = defaultTeam.readFromFile("./input.points");
//		System.out.println("pointsBlanc size : " + points.size());
//
//		ArrayList<Point> DS = defaultTeam.calculConnectedDominatingSet(points, 100);
//		System.out.println("getMIS isValideMIS : " + defaultTeam.isValideMIS(points, DS, 100));
//		System.out.println("getMIS isDominant : " + defaultTeam.isDominant(points, DS, 100));
//		System.out.println("getMIS MIS size : " + DS.size());
//
////		ArrayList<Point> MCDS = defaultTeam.greedyMCDS(points, 100);
//////		System.out.println("greedyMCDS isValideMCDS : " + defaultTeam.isValideMIS(points, MCDS, 100));
//////		System.out.println("greedyMCDS isDominant : " + defaultTeam.isDominant(points, MCDS, 100));
////		System.out.println("greedyMCDS MCDS size : " + MCDS.size());
//
////		ArrayList<Point> pointsMIS = defaultTeam.greedy(points, 100);
////		System.out.println("WX isValideMIS : " + defaultTeam.isValideMIS(points, pointsMIS, 100));
////		System.out.println("WX isDominant : " + defaultTeam.isDominant(points, pointsMIS, 100));
////		System.out.println("WX MIS size : " + pointsMIS.size());
//
////		ArrayList<Point> BBCPoints = defaultTeam.BBC(points, 100);
////		System.out.println("WX BBC isValideMIS : " + defaultTeam.isValideMIS(points, BBCPoints, 100));
////		System.out.println("WX BBC isDominant : " + defaultTeam.isDominant(points, BBCPoints, 100));
////		System.out.println("WX BBC size : " + BBCPoints.size());
//
//	}
//
//}
