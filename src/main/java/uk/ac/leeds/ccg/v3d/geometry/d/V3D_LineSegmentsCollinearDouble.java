/*
 * Copyright 2019 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.v3d.geometry.d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * For representing multiple collinear line segments.
 *
 * Things to do:
 * <ol>
 * <li>Find extremes.</li>
 * </ol>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegmentsCollinearDouble extends V3D_FiniteGeometryDouble {

    private static final long serialVersionUID = 1L;

    /**
     * The collinear line segments.
     */
    public final ArrayList<V3D_LineSegmentDouble> lineSegments;

    /**
     * Create a new instance.
     *
     * @param lineSegments What {@code #lineSegments} is set to.
     */
    public V3D_LineSegmentsCollinearDouble(V3D_LineSegmentDouble... lineSegments) {
        super();
        this.lineSegments = new ArrayList<>();
        this.lineSegments.addAll(Arrays.asList(lineSegments));
    }

    @Override
    public V3D_PointDouble[] getPoints() {
        int nl = lineSegments.size();
        V3D_PointDouble[] r = new V3D_PointDouble[nl * 2];
        for (int i = 0; i < nl; i++) {
            V3D_LineSegmentDouble l = lineSegments.get(i);
            r[i] = l.getP();
            r[i + nl] = l.getQ();
        }
        return r;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + "(";
        Iterator<V3D_LineSegmentDouble> ite = lineSegments.iterator();
        if (!lineSegments.isEmpty()) {
            s += ite.next().toString();
        }
        while (ite.hasNext()) {
            s += ", " + ite.next().toString();
        }
        return s + ")";
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_LineSegmentsCollinearDouble l) {
        HashSet<Integer> indexes = new HashSet<>();
        for (var x : lineSegments) {
            boolean found = false;
            for (int i = 0; i < l.lineSegments.size(); i++) {
                if (x.equals(l.lineSegments.get(i))) {
                    found = true;
                    indexes.add(i);
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        for (int i = 0; i < l.lineSegments.size(); i++) {
            if (!indexes.contains(i)) {
                boolean found = false;
                for (var x : lineSegments) {
                    if (x.equals(l.lineSegments.get(i))) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
        }
        return true;
//        if (!l.lineSegments.containsAll(lineSegments)) {
//            return false;
//        }
//        return lineSegments.containsAll(l.lineSegments);
    }

    /**
     * If p0 and p1 are the same, return the point. Otherwise return a line
     * segment.
     *
     * @param p0 A point which may be the same as p1.
     * @param p1 A point which may be the same as p0.
     * @return A V3D_Point if p0 and p1 are equal, otherwise return a
     * V3D_LineSegment with end points p0 an p1.
     */
    public static V3D_FiniteGeometryDouble getGeometry(V3D_PointDouble p0, 
            V3D_PointDouble p1) {
        if (p0.equals(p1)) {
            return p0;
        } else {
            return new V3D_LineSegmentDouble(p0, p1);
        }

    }

    /**
     * @param l1 A line segment collinear with {@code l2}.
     * @param l2 A line segment collinear with {@code l1}.
     * @return A V3D_LineSegmentsCollinear if {@code l1} and {@code l2} do not
     * intersect, otherwise a single V3D_LineSegment.
     */
    public static V3D_FiniteGeometryDouble getGeometry(V3D_LineSegmentDouble l1,
            V3D_LineSegmentDouble l2) {
        if (l1.getIntersection(l2) == null) {
            return new V3D_LineSegmentsCollinearDouble(l1, l2);
        }
        /**
         * Check the type of intersection. {@code
         * 1)   l1p ---------- l1q
         *         l2p ---------- l2q
         * 2)   l1p ------------------------ l1q
         *         l2p ---------- l2q
         * 3)        l1p ---------- l1q
         *    l2p ------------------------ l2q
         * 4)        l1p ---------- l1q
         *    l2p ---------- l2q
         * 5)   l1q ---------- l1p
         *         l2p ---------- l2q
         * 6)   l1q ------------------------ l1p
         *         l2p ---------- l2q
         * 7)        l1q ---------- l1p
         *    l2p ------------------------ l2q
         * 8)        l1q ---------- l1p
         *    l2p ---------- l2q
         * 9)   l1p ---------- l1q
         *         l2q ---------- l2p
         * 10)   l1p ------------------------ l1q
         *         l2q ---------- l2p
         * 11)       l1p ---------- l1q
         *    l2q ------------------------ l2p
         * 12)       l1p ---------- l1q
         *    l2q ---------- l2p
         * 13)  l1q ---------- l1p
         *         l2q ---------- l2p
         * 14)  l1q ------------------------ l1p
         *         l2q ---------- l2p
         * 15)       l1q ---------- l1p
         *    l2q ------------------------ l2p
         * 16)       l1q ---------- l1p
         *    l2q ---------- l2p
         * }
         */
        V3D_PointDouble l1p = l1.getP();
        V3D_PointDouble l1q = l1.getQ();
        if (l2.isIntersectedBy(l1p)) {
            // Cases 1, 2, 5, 6, 14, 16
            if (l2.isIntersectedBy(l1q)) {
                // Cases 2, 6, 14
                /**
                 * The line segments are effectively the same although the start
                 * and end points may be opposite.
                 */
                //return l1;
                return l2;
            } else {
                V3D_PointDouble l2p = l2.getP();
                // Cases 1, 5, 16
                if (l1.isIntersectedBy(l2p)) {
                    // Cases 5
                    return getGeometry(l1p, l1q);
                } else {
                    // Cases 1, 16
                    return getGeometry(l2p, l1q);
                }
            }
        } else {
            // Cases 3, 4, 7, 8, 9, 10, 11, 12, 13, 15
            if (l2.isIntersectedBy(l1q)) {
                V3D_PointDouble l2p = l2.getP();
                // Cases 4, 8, 9, 10, 11
                if (l1.isIntersectedBy(l2p)) {
                    // Cases 4, 11, 13
                    if (l1.isIntersectedBy(l2.getQ())) {
                        // Cases 11
                        return l2;
                    } else {
                        // Cases 4, 13
                        return getGeometry(l1p, l2.getQ());
                    }
                } else {
                    // Cases 8, 9, 10
                    V3D_PointDouble tq = l2.getQ();
                    if (l1.isIntersectedBy(tq)) {
                        // Cases 8, 9
                        return getGeometry(l2.getQ(), l1q);
                    } else {
                        // Cases 10                      
                        return l1;
                    }
                }
            } else {
                // Cases 3, 7, 12, 15
                V3D_PointDouble tp = l2.getP();
                if (l1.isIntersectedBy(tp)) {
                    // Cases 3, 12, 15
                    V3D_PointDouble tq = l2.getQ();
                    if (l1.isIntersectedBy(tq)) {
                        // Cases 3, 15
                        //return l2;
                        return l1;
                    } else {
                        // Cases 12                 
                        return getGeometry(l2.getP(), l1p);
                    }
                } else {
                    // Cases 7
                    return l2;
                }
            }
        }
    }

    @Override
    public V3D_EnvelopeDouble getEnvelope() {
        if (en == null) {
            Iterator<V3D_LineSegmentDouble> ite = lineSegments.iterator();
            en = ite.next().getEnvelope();
            while (ite.hasNext()) {
                en = en.union(ite.next().getEnvelope());
            }
        }
        return en;
    }

    /**
     * Get the minimum distance to {@code p}.
     *
     * @param p A point.
     * @return The distance squared to {@code p}.
     */
    public double getDistance(V3D_PointDouble p) {
        return Math.sqrt(getDistanceSquared(p));
    }

    /**
     * Get the minimum distance squared to {@code p}.
     *
     * @param p A point.
     * @return The distance squared to {@code p}.
     */
    public double getDistanceSquared(V3D_PointDouble p) {
        if (isIntersectedBy(p)) {
            return 0d;
        }
        Iterator<V3D_LineSegmentDouble> ite = lineSegments.iterator();
        double d = ite.next().getDistanceSquared(p);
        while (ite.hasNext()) {
            d = Math.min(d, ite.next().getDistanceSquared(p));
        }
        return d;
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistance(V3D_LineDouble l) {
        return Math.sqrt(getDistanceSquared(l));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line.
     * @return The minimum distance squared to {@code l}.
     */
    public double getDistanceSquared(V3D_LineDouble l) {
//        if (isIntersectedBy(l)) {
//            return Math_BigRational.ZERO;
//        }
        Iterator<V3D_LineSegmentDouble> ite = lineSegments.iterator();
        double d = ite.next().getDistanceSquared(l);
        while (ite.hasNext()) {
            d = Math.min(d, ite.next().getDistanceSquared(l));
        }
        return d;
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @return The minimum distance to {@code l}.
     */
    public double getDistance(V3D_LineSegmentDouble l) {
        return Math.sqrt(getDistanceSquared(l));
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @return The minimum distance to {@code l}.
     */
    public double getDistanceSquared(V3D_LineSegmentDouble l) {
        if (getIntersection(l) != null) {
            return 0d;
        }
        Iterator<V3D_LineSegmentDouble> ite = lineSegments.iterator();
        double d = ite.next().getDistanceSquared(l);
        while (ite.hasNext()) {
            d = Math.min(d, ite.next().getDistanceSquared(l));
        }
        return d;
    }

    /**
     * Identify if this is intersected by point {@code pt}.
     *
     * @param pt The point to test for intersection with.
     * @return {@code true} iff the geometry is intersected by {@code pt}.
     */
    public boolean isIntersectedBy(V3D_PointDouble pt) {
        Iterator<V3D_LineSegmentDouble> ite = lineSegments.iterator();
        while (ite.hasNext()) {
            if (ite.next().isIntersectedBy(pt)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the intersection between this and the line {@code l}.
     *
     * @param l The line to intersect with.
     * @return The V3D_Geometry.
     */
    public V3D_GeometryDouble getIntersection(V3D_LineDouble l) {
        Iterator<V3D_LineSegmentDouble> ite = lineSegments.iterator();
        V3D_GeometryDouble g = ite.next().getIntersection(l);
        if (g == null) {
            while (ite.hasNext()) {
                g = ite.next().getIntersection(l);
                if (g instanceof V3D_PointDouble) {
                    return g;
                }
            }
            return null;
        } else if (g instanceof V3D_PointDouble) {
            return g;
        } else {
            return this;
        }
    }

    /**
     * Get the intersection between this and the line segment {@code l}. This is
     * complicated in that the result of the intersection could be comprised of
     * points and line segments. Line segments have to have different start and
     * end points.
     *
     * @param ls The line segment to intersect with.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometryDouble getIntersection(V3D_LineSegmentDouble ls) {
        if (lineSegments.get(0).l.isCollinear(ls.l)) {
            ArrayList<V3D_PointDouble> ps = new ArrayList<>();
            ArrayList<V3D_LineSegmentDouble> lse = new ArrayList<>();
            Iterator<V3D_LineSegmentDouble> ite = lineSegments.iterator();
            while (ite.hasNext()) {
                V3D_GeometryDouble g = ite.next().getIntersection(ls);
                if (g != null) {
                    if (g instanceof V3D_PointDouble gp) {
                        ps.add(gp);
                    } else {
                        lse.add((V3D_LineSegmentDouble) g);
                    }
                }
            }
            if (!ps.isEmpty()) {
                // Need to handle cases where we have points.
                throw new UnsupportedOperationException();
            } else {
                if (lse.size() == 1) {
                    return lse.get(0);
                } else {
                    V3D_LineSegmentsCollinearDouble r
                            = new V3D_LineSegmentsCollinearDouble(
                                    lse.toArray(V3D_LineSegmentDouble[]::new));
                    return r.simplify();
                }
            }
        } else {
            // Find the point of intersection if there is one.
            Iterator<V3D_LineSegmentDouble> ite = lineSegments.iterator();
            while (ite.hasNext()) {
                V3D_FiniteGeometryDouble g = ite.next().getIntersection(ls);
                if (g != null) {
                    return g;
                }
            }
            // Return null if there is no point of intersection.
            return null;
        }
    }

    /**
     * Combines overlapping line segments into single line segments. If there is
     * only one line segment, then a V3D_LineSegment is returned, otherwise a
     * V3D_LineSegmentsCollinear is returned.
     *
     * @return Either a V3D_LineSegment or a V3D_LineSegmentsCollinear which is
     * a simplified version of this with overlapping line segments replaced with
     * a single line segment.
     */
    public V3D_FiniteGeometryDouble simplify() {
        ArrayList<V3D_LineSegmentDouble> dummy = new ArrayList<>();
        dummy.addAll(lineSegments);
        ArrayList<V3D_LineSegmentDouble> r = simplify0(dummy, 0);
        if (r.size() == 1) {
            return r.get(0);
        } else {
            return new V3D_LineSegmentsCollinearDouble(r.toArray(V3D_LineSegmentDouble[]::new));
        }
    }

    private ArrayList<V3D_LineSegmentDouble> simplify0(
            ArrayList<V3D_LineSegmentDouble> ls, int i) {
        V3D_LineSegmentDouble l0 = ls.get(i);
        ArrayList<V3D_LineSegmentDouble> r = new ArrayList<>();
        TreeSet<Integer> removeIndexes = new TreeSet<>();
        r.addAll(ls);
        for (int j = i; j < ls.size(); j++) {
            V3D_LineSegmentDouble l1 = ls.get(j);
            if (l0.getIntersection(l1) != null) {
                V3D_PointDouble l0p = l0.getP();
                if (l1.isIntersectedBy(l0p)) {
                    V3D_PointDouble l0q = l0.getQ();
                    if (l1.isIntersectedBy(l0q)) {
                        // l0 is completely overlapped by l1
                        removeIndexes.add(i);
                    } else {
                        V3D_PointDouble l1p = l1.getP();
                        V3D_PointDouble l1q = l1.getQ();
                        if (l0.isIntersectedBy(l1p)) {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegmentDouble(l1q, l0q));
                        } else {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegmentDouble(l1q, l1p));
                        }
                    }
                } else {
                    V3D_PointDouble l0q = l0.getQ();
                    if (l1.isIntersectedBy(l0q)) {
                        V3D_PointDouble l1p = l1.getP();
                        V3D_PointDouble l1q = l1.getQ();
                        if (l0.isIntersectedBy(l1.getP())) {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegmentDouble(l1q, l0p));
                        } else {
                            removeIndexes.add(i);
                            removeIndexes.add(j);
                            r.add(new V3D_LineSegmentDouble(l1p, l0p));
                        }
                    } else {
                        // l1 is completely overlapped by l0
                        removeIndexes.add(j);
                    }
                }
            }
        }
        Iterator<Integer> ite = removeIndexes.descendingIterator();
        while (ite.hasNext()) {
            r.remove(ite.next().intValue());
        }
        if (i < r.size() - 1) {
            r = simplify0(r, i + 1);
        }
        return r;
    }

    @Override
    public void translate(V3D_VectorDouble v) {
        super.translate(v);
        if (en != null) {
            en.translate(v);
        }
        for (int i = 0; i < lineSegments.size(); i++) {
            lineSegments.get(i).translate(v);
        }
    }
    
    @Override
    public V3D_LineSegmentsCollinearDouble rotate(V3D_LineDouble axis, 
            double theta) {
        V3D_LineSegmentDouble[] rls = new V3D_LineSegmentDouble[lineSegments.size()];
        for (int i = 0; i < lineSegments.size(); i++) {
            rls[0] = lineSegments.get(i).rotate(axis, theta);
        }
        return new V3D_LineSegmentsCollinearDouble(rls);
    }

}
