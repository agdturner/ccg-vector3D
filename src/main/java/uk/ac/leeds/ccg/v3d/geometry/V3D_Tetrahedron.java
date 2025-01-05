/*
 * Copyright 2020 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.v3d.geometry;

import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.geometry.Math_Angle;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 *
 * A V3D_Tetrahedron is a V3D_Shape comprising 4 corner V3D_Point points that
 * are not coplanar or collinear or coincident. There are 4 V3D_Triangle
 * triangle faces defined from the outside with the points arranged clockwise.
 * Each face has a V3D_Line_Segment edge equal to that of another face, but
 * these are stored independently in that the order of the points and the
 * directions of the vectors might be opposite. The points are shared between
 * all the triangles. {@code
 pl *- - - - - - - - - - - + - - - - - - - - - - -* qv
     \ ~                                         /|
      \     ~                                   / |
       \         ~                             /  |
        \             ~                       /   |
         \                 ~                 /    |
          \                      ~          /     |
           \                          ~    /      |
            \                             / ~     |
             \                           /       ~s
              \                         /        :
               \                       /
                \                     /      :
                 \                   /
                  \                 /     :
                   \               /
                    \             /    :
                     \           /
                      \         /   :
                       \       /
                        \     /  :
                         \   /
                          \ /:
                           *
                           r
 }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Tetrahedron extends V3D_FiniteGeometry implements V3D_Volume {

    private static final long serialVersionUID = 1L;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector p;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector q;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector r;

    /**
     * For defining one of the points that defines the tetrahedron.
     */
    public V3D_Vector s;

    /**
     * For storing the pqr triangle of the tetrahedron.
     */
    public V3D_Triangle pqr;

    /**
     * For storing the qsr triangle of the tetrahedron.
     */
    public V3D_Triangle qsr;

    /**
     * For storing the spr triangle of the tetrahedron.
     */
    public V3D_Triangle spr;

    /**
     * For storing the psq triangle of the tetrahedron.
     */
    public V3D_Triangle psq;

    /**
     * Create a new instance. {@code pl}, {@code qv}, {@code r} and {@code s}
     * must all be different, not the zero vector and collectively they must be
     * three dimensional. This is generally the fastest way to construct a
     * tetrahedron.
     *
     * @param offset What {@link #offset} is set to.
     * @param p A point that defines the tetrahedron.
     * @param q A point that defines the tetrahedron.
     * @param r A point that defines the tetrahedron.
     * @param s A point that defines the tetrahedron.
     */
    public V3D_Tetrahedron(V3D_Vector offset, V3D_Vector p,
            V3D_Vector q, V3D_Vector r, V3D_Vector s) {
        super(offset);
        this.p = p;
        this.q = q;
        this.r = r;
        this.s = s;
    }

    /**
     * Create a new instance. {@code pl}, {@code qv}, {@code r} and {@code s}
     * must all be different and not coplanar. No test is done to check these
     * things.
     *
     * @param p Used to set {@link #p} and {@link #offset}.
     * @param q Used to set {@link #q}.
     * @param r Used to set {@link #r}.
     * @param s Used to set {@link #s}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Tetrahedron(V3D_Point p, V3D_Point q, V3D_Point r, V3D_Point s,
            int oom, RoundingMode rm) {
        super(p.offset);
        this.p = new V3D_Vector(p.rel);
        V3D_Point qp = new V3D_Point(q);
        qp.setOffset(offset, oom, rm);
        this.q = qp.rel;
        V3D_Point rp = new V3D_Point(r);
        rp.setOffset(offset, oom, rm);
        this.r = rp.rel;
        V3D_Point sp = new V3D_Point(s);
        sp.setOffset(offset, oom, rm);
        this.s = sp.rel;
    }

    /**
     * Create a new instance. {@code pl}, must not be coplanar to t. No test is
     * done to check this is the case.
     *
     * @param p Used to set {@link #p} and {@link #offset}.
     * @param t Used to set {@link #q}, {@link #r} and {@link #s}.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public V3D_Tetrahedron(V3D_Point p, V3D_Triangle t, int oom, RoundingMode rm) {
        this(p, t.getP(), t.getQ(), t.getR(), oom, rm);
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toStringSimple(String pad) {
        return pad + this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFieldsSimple(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFields(String pad) {
        String st = super.toStringFields(pad);
        st += pad + ",\n";
        st += pad + "p=" + p.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "q=" + q.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "r=" + this.r.toString(pad) + "\n";
        st += pad + ",\n";
        st += pad + "s=" + s.toString(pad);// + "\n";
        return st;
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    @Override
    protected String toStringFieldsSimple(String pad) {
        String st = pad + "p=" + p.toStringSimple(pad) + ",\n";
        st += pad + "q=" + q.toStringSimple(pad) + ",\n";
        st += pad + "r=" + this.r.toStringSimple(pad) + ",\n";
        st += pad + "s=" + s.toStringSimple(pad);
        return st;
    }

    @Override
    public V3D_Envelope getEnvelope(int oom) {
        if (en == null) {
//            en = pqr.getEnvelope(oom).union(qsr.getEnvelope(oom));
            en = getP().getEnvelope(oom)
                    .union(getQ().getEnvelope(oom), oom)
                    .union(getR().getEnvelope(oom), oom)
                    .union(getS().getEnvelope(oom), oom);
        }
        return en;
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getP() {
        return new V3D_Point(offset, p);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getQ() {
        return new V3D_Point(offset, q);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getR() {
        return new V3D_Point(offset, r);
    }

    /**
     * @return {@link #p} with {@link #offset} applied.
     */
    public V3D_Point getS() {
        return new V3D_Point(offset, s);
    }

    /**
     * If {@code null} initialise {@link #pqr} and return it.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@link #pqr}
     */
    public V3D_Triangle getPqr(int oom, RoundingMode rm) {
        if (pqr == null) {
            pqr = new V3D_Triangle(offset, p, q, r, oom, rm);
        }
        return pqr;
    }

    /**
     * If {@code null} initialise {@link #qsr} and return it.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@link #qsr}
     */
    public V3D_Triangle getQsr(int oom, RoundingMode rm) {
        if (qsr == null) {
            qsr = new V3D_Triangle(offset, q, s, r, oom, rm);
        }
        return qsr;
    }

    /**
     * If {@code null} initialise {@link #spr} and return it.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@link #spr}
     */
    public V3D_Triangle getSpr(int oom, RoundingMode rm) {
        if (spr == null) {
            spr = new V3D_Triangle(offset, s, p, r, oom, rm);
        }
        return spr;
    }

    /**
     * If {@code null} initialise {@link #psq} and return it.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@link #psq}
     */
    public V3D_Triangle getPsq(int oom, RoundingMode rm) {
        if (psq == null) {
            psq = new V3D_Triangle(offset, p, s, q, oom, rm);
        }
        return psq;
    }

    /**
     * For calculating and returning the surface area.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The outer surface area of the tetrahedron (rounded).
     */
    @Override
    public BigRational getArea(int oom, RoundingMode rm) {
        return getPqr(oom, rm).getArea(oom, rm)
                .add(getQsr(oom, rm).getArea(oom, rm))
                .add(getSpr(oom, rm).getArea(oom, rm))
                .add(getPsq(oom, rm).getArea(oom, rm));
    }

    /**
     * Calculate and return the volume.
     * https://en.wikipedia.org/wiki/Tetrahedron#Volume This implementation is
     * currently a bit rough and ready.
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    @Override
    public BigRational getVolume(int oom, RoundingMode rm) {
        int oomn6 = oom - 6;
        V3D_Triangle tpqr = getPqr(oomn6, rm);
        V3D_Point ts = getS();
        BigRational hd3 = new Math_BigRationalSqrt(
                tpqr.getPl(oomn6, rm).getPointOfProjectedIntersection(ts, oomn6, rm)
                        .getDistanceSquared(ts, oomn6, rm), oomn6, rm)
                .getSqrt(oomn6, rm).divide(3);
        return tpqr.getArea(oomn6 - 3, rm).multiply(hd3);
    }

    /**
     * Calculate and return the centroid as a point.
     *
     * The original implementation used intersection, but it is simpler to get
     * the average of the x, y and z coordinates from the points at each vertex.
     * https://www.globalspec.com/reference/52702/203279/4-8-the-centroid-of-a-tetrahedron
     *
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The centroid point.
     */
    public V3D_Point getCentroid(int oom, RoundingMode rm) {
        oom -= 6;
        BigRational dx = (p.getDX(oom, rm).add(q.getDX(oom, rm))
                .add(r.getDX(oom, rm)).add(s.getDX(oom, rm))).divide(4);
        BigRational dy = (p.getDY(oom, rm).add(q.getDY(oom, rm))
                .add(r.getDY(oom, rm)).add(s.getDY(oom, rm))).divide(4);
        BigRational dz = (p.getDZ(oom, rm).add(q.getDZ(oom, rm))
                .add(r.getDZ(oom, rm)).add(s.getDZ(oom, rm))).divide(4);
        return new V3D_Point(offset, new V3D_Vector(dx, dy, dz));
    }

    /**
     * Identify if this is intersected by point {@code pt}.
     *
     * @param pt The point to test for intersection with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom, RoundingMode rm) {
        pqr = getPqr(oom, rm);
        psq = getPsq(oom, rm);
        spr = getSpr(oom, rm);
        qsr = getQsr(oom, rm);
        if (pqr.getPl(oom, rm).isOnSameSide(pt, getS(), oom, rm)) {
            if (psq.getPl(oom, rm).isOnSameSide(pt, getR(), oom, rm)) {
                if (spr.getPl(oom, rm).isOnSameSide(pt, getQ(), oom, rm)) {
                    if (qsr.getPl(oom, rm).isOnSameSide(pt, getP(), oom, rm)) {
                        return true;
                    }
                }
            }
        }
        if (qsr.isIntersectedBy(pt, oom, rm)) {
            return true;
        }
        if (spr.isIntersectedBy(pt, oom, rm)) {
            return true;
        }
        if (psq.isIntersectedBy(pt, oom, rm)) {
            return true;
        }
        return pqr.isIntersectedBy(pt, oom, rm);
    }
    
    /**
     * @param r The ray for which the intersection is returned.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The intersection of this and r.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Ray r, int oom, RoundingMode rm) {
        V3D_FiniteGeometry irl = getIntersection(r.l, oom, rm);
        if (irl == null) {
            return null;
        }
        if (irl instanceof V3D_Point irlp) {
            if (r.isAligned(irlp, oom, rm)) {
                return irl;
            } else {
                return null;
            }
        }
        return ((V3D_LineSegment) irl).clip(r.getPl(), r.l.getQ(oom, rm), oom, rm);
//        V3D_FiniteGeometry pqri = getPqr(oom, rm).getIntersection(r, oom, rm);
//        if (pqri == null) {
//            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(r, oom, rm);
//            if (psqi == null) {
//                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(r, oom, rm);
//                if (qsri == null) {
//                    V3D_Geometry spri = getSpr(oom, rm).getIntersection(r, oom, rm);
//                    if (spri == null) {
//                        return null;
//                    } else {
//                        return V3D_LineSegment.getGeometry(r.l.getP(), (V3D_Point) spri, oom, rm);
//                    }
//                } else if (qsri instanceof V3D_Point qsrip) {
//                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(r, oom, rm);
//                    if (spri == null) {
//                        return V3D_LineSegment.getGeometry(r.l.getP(), qsrip, oom, rm);
//                    } else if (spri instanceof V3D_Point sprip) {
//                        if (qsrip.equals(sprip, oom, rm)) {
//                            return V3D_LineSegment.getGeometry(r.l.getP(), qsrip, oom, rm);
//                        } else {
//                            return new V3D_LineSegment(qsrip, sprip, oom, rm);
//                        }
//                    } else {
//                        return spri;
//                    }
//                } else {
//                    return qsri;
//                }
//            } else if (psqi instanceof V3D_Point psqip) {
//                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(r, oom, rm);
//                if (qsri == null) {
//                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(r, oom, rm);
//                    if (spri == null) {
//                        return V3D_LineSegment.getGeometry(r.l.getP(), psqip, oom, rm);
//                    } else if (spri instanceof V3D_Point sprip) {
//                        if (psqip.equals(sprip, oom, rm)) {
//                            return V3D_LineSegment.getGeometry(r.l.getP(), psqip, oom, rm);
//                        } else {
//                            return new V3D_LineSegment(psqip, sprip, oom, rm);
//                        }
//                    } else {
//                        return spri;
//                    }
//                } else if (qsri instanceof V3D_Point qsrip) {
//                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(r, oom, rm);
//                    if (spri == null) {
//                        if (psqip.equals(qsrip, oom, rm)) {
//                            return V3D_LineSegment.getGeometry(r.l.getP(), psqip, oom, rm);
//                        } else {
//                            return new V3D_LineSegment(psqip, qsrip, oom, rm);
//                        }
//                    } else if (spri instanceof V3D_Point sprip) {
//                        if (psqip.equals(qsrip, oom, rm)) {
//                            if (psqip.equals(sprip, oom, rm)) {
//                                return V3D_LineSegment.getGeometry(r.l.getP(), psqip, oom, rm);
//                            } else {
//                                return new V3D_LineSegment(psqip, sprip, oom, rm);
//                            }
//                        } else {
//                            return new V3D_LineSegment(psqip, qsrip, oom, rm);
//                        }
//                    } else {
//                        return spri;
//                    }
//                } else {
//                    return qsri;
//                }
//            } else {
//                return psqi;
//            }
//        } else if (pqri instanceof V3D_Point pqrip) {
//            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(r, oom, rm);
//            if (psqi == null) {
//                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(r, oom, rm);
//                if (qsri == null) {
//                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(r, oom, rm);
//                    if (spri == null) {
//                        return V3D_LineSegment.getGeometry(r.l.getP(), pqrip, oom, rm);
//                    } else if (spri instanceof V3D_Point sprip) {
//                        if (pqrip.equals(sprip, oom, rm)) {
//                            return V3D_LineSegment.getGeometry(r.l.getP(), pqrip, oom, rm);
//                        } else {
//                            return new V3D_LineSegment(pqrip, sprip, oom, rm);
//                        }
//                    } else {
//                        return spri;
//                    }
//                } else if (qsri instanceof V3D_Point qsrip) {
//                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(r, oom, rm);
//                    if (spri == null) {
//                        if (pqrip.equals(qsrip, oom, rm)) {
//                            return V3D_LineSegment.getGeometry(r.l.getP(), qsrip, oom, rm);
//                        } else {
//                            return new V3D_LineSegment(pqrip, qsrip, oom, rm);
//                        }
//                    } else if (spri instanceof V3D_Point sprip) {
//                        if (pqrip.equals(qsrip, oom, rm)) {
//                            if (qsrip.equals(sprip, oom, rm)) {
//                                return V3D_LineSegment.getGeometry(r.l.getP(), qsrip, oom, rm);
//                            } else {
//                                return new V3D_LineSegment(qsrip, sprip, oom, rm);
//                            }
//                        } else {
//                            return V3D_LineSegment.getGeometry(pqrip, qsrip, oom, rm);
//                        }
//                    } else {
//                        return spri;
//                    }
//                } else {
//                    return qsri;
//                }
//            } else if (psqi instanceof V3D_Point psqip) {
//                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(r, oom, rm);
//                if (qsri == null) {
//                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(r, oom, rm);
//                    if (spri == null) {
//                        if (pqrip.equals(psqip, oom, rm)) {
//                            return V3D_LineSegment.getGeometry(r.l.getP(), pqrip, oom, rm);
//                        } else {
//                            return new V3D_LineSegment(pqrip, psqip, oom, rm);
//                        }
//                    } else if (spri instanceof V3D_Point sprip) {
//                        if (pqrip.equals(psqip, oom, rm)) {
//                            if (psqip.equals(sprip, oom, rm)) {
//                                return V3D_LineSegment.getGeometry(r.l.getP(), psqip, oom, rm);
//                            } else {
//                                return new V3D_LineSegment(psqip, sprip, oom, rm);
//                            }
//                        } else {
//                            return new V3D_LineSegment(pqrip, psqip, oom, rm);
//                        }
//                    } else {
//                        return spri;
//                    }
//                } else if (qsri instanceof V3D_Point qsrip) {
//                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(r, oom, rm);
//                    if (spri == null) {
//                        if (pqrip.equals(psqip, oom, rm)) {
//                            if (psqip.equals(qsrip, oom, rm)) {
//                                return V3D_LineSegment.getGeometry(r.l.getP(), psqip, oom, rm);
//                            } else {
//                                return new V3D_LineSegment(psqip, qsrip, oom, rm);
//                            }
//                        } else {
//                            return new V3D_LineSegment(pqrip, psqip, oom, rm);
//                        }
//                    } else if (spri instanceof V3D_Point sprip) {
//                        if (pqrip.equals(psqip, oom, rm)) {
//                            if (psqip.equals(qsrip, oom, rm)) {
//                                return V3D_LineSegment.getGeometry(pqrip, sprip, oom, rm);
//                            } else {
//                                return new V3D_LineSegment(psqip, qsrip, oom, rm);
//                            }
//                        } else {
//                            return new V3D_LineSegment(pqrip, psqip, oom, rm);
//                        }
//                    } else {
//                        return spri;
//                    }
//                } else {
//                    return qsri;
//                }
//            } else {
//                return psqi;
//            }
//        } else {
//            return pqri;
//        }
    }

    /**
     * Get the intersection between this and the line {@code l}.
     *
     * @param l The line to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Line l, int oom, RoundingMode rm) {
        V3D_FiniteGeometry pqri = getPqr(oom, rm).getIntersection(l, oom, rm);
        if (pqri == null) {
            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
            if (psqi == null) {
                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                if (qsri == null) {
                    return null;
                }
                if (qsri instanceof V3D_LineSegment){
                    return qsri;
                }
                V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                if (spri == null) {
                    return null;
                }
                return V3D_LineSegment.getGeometry((V3D_Point) qsri, 
                        (V3D_Point) spri, oom, rm);
            } else {
                if (psqi instanceof V3D_LineSegment){
                    return psqi;
                }
                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                if (qsri == null) {
                    return psqi;
                }
                if (qsri instanceof V3D_LineSegment){
                    return qsri;
                }
                return V3D_LineSegment.getGeometry((V3D_Point) psqi, 
                        (V3D_Point) qsri, oom, rm);
            }
        } else if (pqri instanceof V3D_Point pqrip) {
            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
            if (psqi == null) {
                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                if (qsri == null) {
                    return null;
                } else if (qsri instanceof V3D_Point qsrip) {
                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(qsrip, pqrip, oom, rm);
                    } else if (spri instanceof V3D_Point sprip) {
                        return V3D_Triangle.getGeometry(qsrip, pqrip, sprip, oom, rm);
                    } else {
                        return spri;
                    }
                } else {
                    return qsri;
                }
            } else if (psqi instanceof V3D_Point psqip) {
                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                if (qsri == null) {
                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(psqip, pqrip, oom, rm);
                    } else if (spri instanceof V3D_Point sprip) {
                        return V3D_Triangle.getGeometry(psqip, pqrip, sprip, oom, rm);
                    } else {
                        return spri;
                    }
                } else if (qsri instanceof V3D_Point qsrip) {
                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                    if (spri == null) {
                        return V3D_LineSegment.getGeometry(psqip, pqrip, oom, rm);
                    } else if (spri instanceof V3D_Point sprip) {
                        return getGeometry(pqrip, psqip, qsrip, sprip, oom, rm);
                    } else {
                        return spri;
                    }
                } else {
                    return qsri;
                }
            } else {
                return psqi;
            }
        } else {
            return pqri;
        }
    }

    /**
     * If p, q, r and s are equal then the point is returned. If there are just
     * two different points then a line segment is returned. If 3 points are
     * different then a triangle is returned. If 4 points are different then if
     * they are coplanar, a V3D_TriangleCoplanar is returned, otherwise a
     * tetrahedron is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param s Another possibly equal point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, qv)} or
     * {@code new V3D_Triangle(pl, qv, r)}
     */
    public static V3D_FiniteGeometry getGeometry(V3D_Point p, V3D_Point q,
            V3D_Point r, V3D_Point s, int oom, RoundingMode rm) {
        if (p.equals(q, oom, rm)) {
            return V3D_Triangle.getGeometry(p, r, s, oom, rm);
        } else if (p.equals(r, oom, rm)) {
            return V3D_Triangle.getGeometry(p, q, s, oom, rm);
        } else if (p.equals(s, oom, rm)) {
            return V3D_Triangle.getGeometry(p, q, r, oom, rm);
        } else if (q.equals(r, oom, rm)) {
            return V3D_Triangle.getGeometry(p, q, s, oom, rm);
        } else if (q.equals(s, oom, rm)) {
            return V3D_Triangle.getGeometry(p, q, r, oom, rm);
        } else if (r.equals(s, oom, rm)) {
            return V3D_Triangle.getGeometry(p, q, r, oom, rm);
        } else {
            if (V3D_Plane.isCoplanar(oom, rm, p, q, r, s)) {
                if (V3D_Line.isCollinear(oom, rm, p, q, r, s)) {
                    //return V3D_Line(pl, qv, r, s);
                    throw new UnsupportedOperationException("Need code to construct a line segment from 4 points!");
                }
                V3D_LineSegment pq = new V3D_LineSegment(p, q, oom, rm);
                V3D_LineSegment rs = new V3D_LineSegment(r, s, oom, rm);
                if (pq.getIntersection(rs, oom, rm) != null) {
                    return new V3D_Polygon(
                            new V3D_Triangle(p, q, r, oom, rm),
                            new V3D_Triangle(p, q, s, oom, rm));
                } else {
                    V3D_LineSegment pr = new V3D_LineSegment(p, r, oom, rm);
                    V3D_LineSegment qs = new V3D_LineSegment(q, s, oom, rm);
                    if (pr.getIntersection(qs, oom, rm) != null) {
                        return new V3D_Polygon(
                                new V3D_Triangle(p, r, q, oom, rm),
                                new V3D_Triangle(p, r, s, oom, rm));
                    } else {
                        return new V3D_Polygon(
                                new V3D_Triangle(p, q, s, oom, rm),
                                new V3D_Triangle(p, r, s, oom, rm));
                    }
                }
            }
            return new V3D_Tetrahedron(p, q, r, s, oom, rm);
        }
    }

    /**
     * At least two of the points input are expected to be the same. If all
     * points are equal then the point is returned. If there are just two
     * different points then a line segment is returned. If 3 points are
     * different then a triangle is returned. If 4 points are different then if
     * they are coplanar, a V3D_TriangleCoplanar is returned, otherwise a
     * tetrahedron is returned.
     *
     * @param p A point.
     * @param q Another possibly equal point.
     * @param r Another possibly equal point.
     * @param s Another possibly equal point.
     * @param t Another possibly equal point.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return either {@code pl} or {@code new V3D_LineSegment(pl, qv)} or
     * {@code new V3D_Triangle(pl, qv, r)}
     */
    protected static V3D_FiniteGeometry getGeometry(V3D_Point p, V3D_Point q,
            V3D_Point r, V3D_Point s, V3D_Point t, int oom, RoundingMode rm) {
        if (p.equals(q, oom, rm)) {
            return getGeometry(p, r, s, t, oom, rm);
        } else if (p.equals(r, oom, rm)) {
            return getGeometry(p, q, s, t, oom, rm);
        } else if (p.equals(s, oom, rm)) {
            return getGeometry(p, q, r, t, oom, rm);
        } else if (p.equals(t, oom, rm)) {
            return getGeometry(p, q, r, s, oom, rm);
        } else if (q.equals(r, oom, rm)) {
            return getGeometry(p, q, s, t, oom, rm);
        } else if (q.equals(s, oom, rm)) {
            return getGeometry(p, q, r, t, oom, rm);
        } else if (q.equals(t, oom, rm)) {
            return getGeometry(p, q, r, s, oom, rm);
        } else if (r.equals(s, oom, rm)) {
            return getGeometry(p, q, r, t, oom, rm);
        } else if (r.equals(t, oom, rm)) {
            return getGeometry(p, q, r, s, oom, rm);
        } else {
            return getGeometry(p, q, r, t, oom, rm);
        }
    }

    /**
     * Get the intersection between this and the line segment {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_LineSegment l, int oom,
            RoundingMode rm) {
        V3D_FiniteGeometry g = getIntersection(l.l, oom, rm);
        if (g == null) {
            return null;
        } else {
            if (g instanceof V3D_Point gp) {
                if (isIntersectedBy(gp, oom, rm)) {
                    return g;
                } else {
                    return null;
                }
            } else {
                V3D_LineSegment li = (V3D_LineSegment) g;
                V3D_Point lip = li.getP();
                V3D_Point liq = li.getQ();
                V3D_Point lp = l.getP();
                V3D_Point lq = l.getQ();
                if (isIntersectedBy(lp, oom, rm)) {
                    if (isIntersectedBy(lq, oom, rm)) {
                        return li.getIntersection(l, oom, rm);
                    } else {
                        V3D_FiniteGeometry pqri = getPqr(oom, rm).getIntersection(l, oom, rm);
                        if (pqri == null) {
                            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
                            if (psqi == null) {
                                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                if (qsri == null) {
                                    return new V3D_LineSegment(lip,
                                            (V3D_Point) getSpr(oom, rm).getIntersection(l, oom, rm), oom, rm);
                                } else {
                                    if (qsri instanceof V3D_Point qsrip) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return new V3D_LineSegment(lip, qsrip, oom, rm);
                                        } else {
                                            return V3D_Triangle.getGeometry(lip, qsrip, (V3D_Point) spri, oom, rm);
                                        }
                                    } else {
                                        return qsri;
                                    }
                                }
                            } else {
                                if (psqi instanceof V3D_Point psqip) {
                                    V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                    if (qsri == null) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return new V3D_LineSegment(lip, psqip, oom, rm);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return V3D_Triangle.getGeometry(lip, psqip, sprip, oom, rm);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point qsrip) {
                                            V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                            if (spri == null) {
                                                return V3D_Triangle.getGeometry(lip, psqip, qsrip, oom, rm);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
                                                    return getGeometry(lip, psqip, qsrip, sprip, oom, rm);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            return qsri;
                                        }
                                    }
                                } else {
                                    return psqi;
                                }
                            }
                        } else {
                            if (pqri instanceof V3D_Point pqrip) {
                                V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
                                if (psqi == null) {
                                    V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                    if (qsri == null) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return new V3D_LineSegment(lip, pqrip, oom, rm);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return V3D_Triangle.getGeometry(lip, pqrip, sprip, oom, rm);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point qsrip) {
                                            V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                            if (spri == null) {
                                                //return V3D_Triangle.getGeometry(lip, pqrip, qsrip, oom, rm);
                                                return V3D_Triangle.getGeometry(lp, pqrip, qsrip, oom, rm);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
                                                    //return getGeometry(lip, pqrip, qsrip, sprip, oom, rm);
                                                    return getGeometry(liq, pqrip, qsrip, sprip, oom, rm);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            return qsri;
                                        }
                                    }
                                } else {
                                    if (psqi instanceof V3D_Point psqip) {
                                        V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                        if (qsri == null) {
                                            V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                            if (spri == null) {
                                                return V3D_Triangle.getGeometry(lip, pqrip, psqip, oom, rm);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
                                                    return getGeometry(lip, pqrip, psqip, sprip, oom, rm);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            if (qsri instanceof V3D_Point qsrip) {
                                                V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                                if (spri == null) {
                                                    return getGeometry(lip, psqip, pqrip, qsrip, oom, rm);
                                                } else {
                                                    if (spri instanceof V3D_Point sprip) {
                                                        return getGeometry(lip, psqip, pqrip, qsrip, sprip, oom, rm);
                                                    } else {
                                                        return spri;
                                                    }
                                                }
                                            } else {
                                                return qsri;
                                            }
                                        }
                                    } else {
                                        return psqi;
                                    }
                                }
                            } else {
                                return pqri;
                            }
                        }
                    }
                } else {
                    V3D_FiniteGeometry pqri = getPqr(oom, rm).getIntersection(l, oom, rm);
                    if (pqri == null) {
                        V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
                        if (psqi == null) {
                            V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                            if (qsri == null) {
                                return new V3D_LineSegment(liq,
                                        (V3D_Point) getSpr(oom, rm).getIntersection(l, oom, rm), oom, rm);
                            } else {
                                if (qsri instanceof V3D_Point qsrip) {
                                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                    if (spri == null) {
                                        return new V3D_LineSegment(liq, qsrip, oom, rm);
                                    } else {
                                        return V3D_Triangle.getGeometry(liq, qsrip, (V3D_Point) spri, oom, rm);
                                    }
                                } else {
                                    return qsri;
                                }
                            }
                        } else {
                            if (psqi instanceof V3D_Point psqip) {
                                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                if (qsri == null) {
                                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                    if (spri == null) {
                                        return new V3D_LineSegment(liq, psqip, oom, rm);
                                    } else {
                                        if (spri instanceof V3D_Point sprip) {
                                            return V3D_Triangle.getGeometry(liq, psqip, sprip, oom, rm);
                                        } else {
                                            return spri;
                                        }
                                    }
                                } else {
                                    if (qsri instanceof V3D_Point qsrip) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return V3D_Triangle.getGeometry(liq, psqip, qsrip, oom, rm);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return getGeometry(liq, psqip, qsrip, sprip, oom, rm);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        return qsri;
                                    }
                                }
                            } else {
                                return psqi;
                            }
                        }
                    } else {
                        if (pqri instanceof V3D_Point pqrip) {
                            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(l, oom, rm);
                            if (psqi == null) {
                                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                if (qsri == null) {
                                    V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                    if (spri == null) {
                                        return new V3D_LineSegment(liq, pqrip, oom, rm);
                                    } else {
                                        if (spri instanceof V3D_Point sprip) {
                                            return V3D_Triangle.getGeometry(liq, pqrip, sprip, oom, rm);
                                        } else {
                                            return spri;
                                        }
                                    }
                                } else {
                                    if (qsri instanceof V3D_Point qsrip) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return V3D_Triangle.getGeometry(liq, pqrip, qsrip, oom, rm);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return getGeometry(liq, pqrip, qsrip, sprip, oom, rm);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        return qsri;
                                    }
                                }
                            } else {
                                if (psqi instanceof V3D_Point psqip) {
                                    V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(l, oom, rm);
                                    if (qsri == null) {
                                        V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                        if (spri == null) {
                                            return V3D_Triangle.getGeometry(liq, pqrip, psqip, oom, rm);
                                        } else {
                                            if (spri instanceof V3D_Point sprip) {
                                                return getGeometry(liq, pqrip, psqip, sprip, oom, rm);
                                            } else {
                                                return spri;
                                            }
                                        }
                                    } else {
                                        if (qsri instanceof V3D_Point qsrip) {
                                            V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(l, oom, rm);
                                            if (spri == null) {
                                                return getGeometry(liq, psqip, pqrip, qsrip, oom, rm);
                                            } else {
                                                if (spri instanceof V3D_Point sprip) {
                                                    return getGeometry(liq, psqip, pqrip, qsrip, sprip, oom, rm);
                                                } else {
                                                    return spri;
                                                }
                                            }
                                        } else {
                                            return qsri;
                                        }
                                    }
                                } else {
                                    return psqi;
                                }
                            }
                        } else {
                            return pqri;
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the intersection between the geometry and the plane {@code p}. The
     * intersection will be null, a point, a line segment, a triangle or a
     * quadrilateral. It should be that any points of intersection are within
     * this.
     *
     * @param pl The plane to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Plane pl, int oom, RoundingMode rm) {
        V3D_FiniteGeometry pqri = getPqr(oom, rm).getIntersection(pl, oom, rm);
        if (pqri == null) {
            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(pl, oom, rm);
            if (psqi == null) {
                return null;
            } else if (psqi instanceof V3D_Point psqip) {
                // psqip must be the point s!
                return psqip;
            } else if (psqi instanceof V3D_LineSegment psqil) {
                /**
                 * There will also be a line segment of qsr and spr that
                 * together form a triangle.
                 */
                V3D_LineSegment qsril = (V3D_LineSegment) getQsr(oom, rm).getIntersection(pl, oom, rm);
                V3D_LineSegment spril = (V3D_LineSegment) getSpr(oom, rm).getIntersection(pl, oom, rm);
                return V3D_Triangle.getGeometry(psqil, qsril, spril, oom, rm);
            } else {
                // Triangle
                return (V3D_Triangle) psqi;
            }
        } else if (pqri instanceof V3D_Point pqrip) {
            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(pl, oom, rm);
            if (psqi == null) {
                return pqrip;
            } else if (psqi instanceof V3D_Point psqip) {
                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(pl, oom, rm);
                if (qsri == null) {
                    return psqip;
                } else {
                    return V3D_Triangle.getGeometry((V3D_LineSegment) qsri, psqip, oom, rm);
                }
            } else if (psqi instanceof V3D_LineSegment psqil) {
                V3D_FiniteGeometry qsri = getQsr(oom, rm).getIntersection(pl, oom, rm);
                if (qsri == null) {
                    return psqil;
                } else {
                    if (qsri instanceof V3D_Point) {
                        return psqil;
                    } else {
                        //V3D_LineSegment spri = (V3D_LineSegment) getSpr(oom, rm).getIntersection(pl, oom, rm);
                        return V3D_Triangle.getGeometry2(psqil, (V3D_LineSegment) qsri, oom, rm);
                    }
                }
            } else {
                // Triangle
                return (V3D_Triangle) psqi;
            }
        } else if (pqri instanceof V3D_LineSegment pqril) {
            V3D_FiniteGeometry psqi = getPsq(oom, rm).getIntersection(pl, oom, rm);
            if (psqi == null) {
                V3D_LineSegment qsril = (V3D_LineSegment) getQsr(oom, rm).getIntersection(pl, oom, rm);
                V3D_LineSegment spril = (V3D_LineSegment) getSpr(oom, rm).getIntersection(pl, oom, rm);
                return V3D_Triangle.getGeometry(pqril, qsril, spril, oom, rm);
            } else if (psqi instanceof V3D_Point) {
                V3D_FiniteGeometry spri = getSpr(oom, rm).getIntersection(pl, oom, rm);
                if (spri instanceof V3D_Point sprip) {
                    return V3D_Triangle.getGeometry(pqril, sprip, oom, rm);
                } else {
                    return V3D_Triangle.getGeometry2((V3D_LineSegment) spri, pqril, oom, rm);
                }
            } else if (psqi instanceof V3D_LineSegment psqil) {
                return V3D_Triangle.getGeometry2(psqil, pqril, oom, rm);
            } else {
                // Triangle
                return (V3D_Triangle) psqi;
            }
        } else {
            return (V3D_Triangle) pqri;
        }
    }

    /**
     * Get the intersection between this and the triangle {@code t}. The
     * intersection will be null, a point, a line segment, a triangle,
     * quadrilateral, pentagon or a hexagon.
     *
     * @param t The triangle to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Triangle t, int oom, RoundingMode rm) {
        V3D_FiniteGeometry i = getIntersection(t.getPl(oom, rm), oom, rm);
        if (i == null) {
            return null;
        } else {
            if (i instanceof V3D_Point ip) {
                if (t.isAligned(ip, oom, rm)) {
                    //if (t.isIntersectedBy(pip, oom, rm)) {
                    return i;
                } else {
                    return null;
                }
            } else if (i instanceof V3D_LineSegment il) {
                /**
                 * Need to get the intersections of pil and the plane edges of
                 * the triangle.
                 */
                V3D_Vector n = t.getPl(oom, rm).n;
                V3D_Plane lp = new V3D_Plane(offset, t.p, t.q, t.p.add(n, oom, rm), oom, rm);
                V3D_FiniteGeometry lpiil = lp.getIntersection(il, oom, rm);
                if (lpiil == null) {
                    V3D_Plane lq = new V3D_Plane(offset, t.q, t.r, t.q.add(n, oom, rm), oom, rm);
                    V3D_FiniteGeometry lqiil = lq.getIntersection(il, oom, rm);
                    if (lqiil == null) {
                        V3D_Plane lr = new V3D_Plane(offset, t.r, t.p, t.r.add(n, oom, rm), oom, rm);
                        V3D_FiniteGeometry lriil = lr.getIntersection(il, oom, rm);
                        if (lriil == null) {
                            return il;
                        } else if (lriil instanceof V3D_Point lriilp) {
                            // Find the other point and return the line segment.
                            V3D_Point pq = t.getQ();
                            if (lr.isOnSameSide(lriilp, pq, oom, rm)) {
                                return new V3D_LineSegment(lriilp, pq, oom, rm);
                            } else {
                                return new V3D_LineSegment(lriilp, t.getP(), oom, rm);
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegment) lriil;
                        }
                    } else if (lqiil instanceof V3D_Point lqiilp) {
                        // Find the other point and return the linesegment.
                        V3D_Plane lr = new V3D_Plane(offset, t.r, t.p, t.r.add(n, oom, rm), oom, rm);
                        V3D_FiniteGeometry lriil = lr.getIntersection(il, oom, rm);
                        if (lriil == null) {
                            // For the points on the right side (if any)
                            V3D_Point ilp = il.getP();
                            V3D_Point ilq = il.getQ();
                            V3D_Point tpp = t.getP();
                            if (lq.isOnSameSide(ilp, tpp, oom, rm)) {
                                if (lq.isOnSameSide(ilq, tpp, oom, rm)) {
                                    if (lqiilp.getDistanceSquared(ilp, oom, rm).compareTo(
                                            lqiilp.getDistanceSquared(ilq, oom, rm)) == -1) {
                                        return new V3D_LineSegment(lqiilp, ilq, oom, rm);
                                    } else {
                                        return new V3D_LineSegment(lqiilp, ilp, oom, rm);
                                    }
                                } else {
                                    return new V3D_LineSegment(lqiilp, ilp, oom, rm);
                                }
                            } else {
                                //if (lq.isOnSameSide(pilq, tpp, oom, rm)) {
                                return new V3D_LineSegment(lqiilp, ilq, oom, rm);
                                //} else {
                                //    // This should not happen!
                                //}
                            }
                        } else if (lriil instanceof V3D_Point lriilp) {
                            if (lqiilp.equals(lriilp, oom, rm)) {
                                // Find the other point and return the line segment.
                                V3D_Point ilp = il.getP();
                                V3D_Point ilq = il.getQ();
                                if (lq.isOnSameSide(ilp, ilq, oom, rm)) {
                                    if (lqiilp.getDistanceSquared(ilp, oom, rm).compareTo(
                                            lqiilp.getDistanceSquared(ilq, oom, rm)) == -1) {
                                        return new V3D_LineSegment(lqiilp, ilq, oom, rm);
                                    } else {
                                        return new V3D_LineSegment(lqiilp, ilp, oom, rm);
                                    }
                                } else {
                                    return new V3D_LineSegment(lqiilp, ilp, oom, rm);
                                }
                            } else {
                                return new V3D_LineSegment(lriilp, lqiilp, oom, rm);
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegment) lriil;
                        }
                    } else {
                        // Return the line segment.
                        return (V3D_LineSegment) lqiil;
                    }
                } else if (lpiil instanceof V3D_Point lpiilp) {
                    // Find the other point and return the linesegment.
                    V3D_Plane lq = new V3D_Plane(offset, t.q, t.r, t.q.add(n, oom, rm), oom, rm);
                    V3D_FiniteGeometry lqiil = lq.getIntersection(il, oom, rm);
                    if (lqiil == null) {
                        V3D_Plane lr = new V3D_Plane(offset, t.r, t.p, t.r.add(n, oom, rm), oom, rm);
                        V3D_FiniteGeometry lriil = lr.getIntersection(il, oom, rm);
                        if (lriil == null) {
                            // Find the other point and return the line segment.
                            V3D_Point ilp = il.getP();
                            V3D_Point ilq = il.getQ();
                            if (lq.isOnSameSide(ilp, ilq, oom, rm)) {
                                if (lpiilp.getDistanceSquared(ilp, oom, rm).compareTo(
                                        lpiilp.getDistanceSquared(ilq, oom, rm)) == -1) {
                                    return new V3D_LineSegment(lpiilp, ilq, oom, rm);
                                } else {
                                    return new V3D_LineSegment(lpiilp, ilp, oom, rm);
                                }
                            } else {
                                return new V3D_LineSegment(lpiilp, ilp, oom, rm);
                            }
                        } else if (lriil instanceof V3D_Point lriilp) {
                            if (lpiilp.equals(lriilp, oom, rm)) {
                                // Find the other point and return the line segment.
                                V3D_Point ilp = il.getP();
                                V3D_Point ilq = il.getQ();
                                if (lq.isOnSameSide(ilp, ilq, oom, rm)) {
                                    if (lpiilp.getDistanceSquared(ilp, oom, rm).compareTo(
                                            lpiilp.getDistanceSquared(ilq, oom, rm)) == -1) {
                                        return new V3D_LineSegment(lpiilp, ilq, oom, rm);
                                    } else {
                                        return new V3D_LineSegment(lpiilp, ilp, oom, rm);
                                    }
                                } else {
                                    if (lp.isOnSameSide(t.getR(), ilp, oom, rm)) {
                                        return new V3D_LineSegment(lpiilp, ilp, oom, rm);
                                    } else {
                                        return new V3D_LineSegment(lpiilp, ilq, oom, rm);
                                    }
                                }
                            } else {
                                return new V3D_LineSegment(lpiilp, lriilp, oom, rm);
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegment) lriil;
                        }
                    } else if (lqiil instanceof V3D_Point lqiilp) {
                        // Find the other point and return the linesegment.
                        V3D_Plane lr = new V3D_Plane(offset, t.r, t.p, t.r.add(n, oom, rm), oom, rm);
                        V3D_FiniteGeometry lriil = lr.getIntersection(il, oom, rm);
                        if (lriil == null) {
                            // For the points on the right side (if any)
                            V3D_Point pilp = il.getP();
                            V3D_Point pilq = il.getQ();
                            if (lq.isOnSameSide(pilp, pilq, oom, rm)) {
                                if (lqiilp.getDistanceSquared(pilp, oom, rm).compareTo(
                                        lqiilp.getDistanceSquared(pilq, oom, rm)) == -1) {
                                    return new V3D_LineSegment(lqiilp, pilq, oom, rm);
                                } else {
                                    return new V3D_LineSegment(lqiilp, pilp, oom, rm);
                                }
                            } else {
                                if (lq.isOnSameSide(pilp, t.getP(), oom, rm)) {
                                    return new V3D_LineSegment(lqiilp, pilp, oom, rm);
                                } else {
                                    return new V3D_LineSegment(lqiilp, pilq, oom, rm);
                                }
                            }
                        } else if (lriil instanceof V3D_Point lriilp) {
                            if (lriilp.equals(lpiilp, oom, rm)) {
                                return new V3D_LineSegment(lriilp, lqiilp, oom, rm);
                            } else {
                                return new V3D_LineSegment(lriilp, lpiilp, oom, rm);
                            }
                        } else {
                            // Return the line segment.
                            return (V3D_LineSegment) lriil;
                        }
                    } else {
                        // Return the line segment.
                        return (V3D_LineSegment) lqiil;
                    }
                } else {
                    // Return the line segment.
                    return (V3D_LineSegment) lpiil;
                }
                //return t.getIntersection(pil, oom, rm);
            } else if (i instanceof V3D_Triangle it) {
                //it.getIntersection(t, oom, rm);
                return it.clip(t, t.getCentroid(oom, rm), oom, rm);
//                //return it.getIntersection(t, oom); // This does not work due to precision issues.
//                /**
//                 * If all the points of t are within the planes of it, then
//                 * return t. If any edges of t intersect the planes of it, then further
//                 * intersections are needed to derive the final shape. Otherwise
//                 * return it.
//                 */
//                V3D_Point tp = t.pl.getP();
//                V3D_Point tq = t.pl.getQ();
//                V3D_Point tr = t.pl.getR();
//                V3D_Point itp = it.pl.getP();
//                V3D_Point itq = it.pl.getQ();
//                V3D_Point itr = it.pl.getR();
//                V3D_Vector itn = it.pl.getN(oom);
//                V3D_Point itpp = new V3D_Point(e, itp.offset.add(itn, oom), itp.rel);
//                V3D_Plane itppl = new V3D_Plane(itp, itq, itpp);
//                V3D_Point itqp = new V3D_Point(e, itq.offset.add(itn, oom), itq.rel);
//                V3D_Plane itqpl = new V3D_Plane(itq, itr, itqp);
//                V3D_Point itrp = new V3D_Point(e, itr.offset.add(itn, oom), itr.rel);
//                V3D_Plane itrpl = new V3D_Plane(itr, itp, itrp);
//                V3D_FiniteGeometry itc = it.clip(itppl, tr, oom);
//                if (itc == null) {
//                    return null;
//                } else if (itc instanceof V3D_Point) {
//                    return itc;
//                } else if (itc instanceof V3D_LineSegment) {
//                    
//                } else if (itc instanceof V3D_Triangle) {
//                    
//                } else {
//                    // itc instanceof V3D_ConvexHullCoplanar
//                
//                }
//                // There are 512 cases to deal with!
//                if (itppl.isOnSameSide(tp, itr, oom)) {
//                    if (itppl.isOnSameSide(tq, itr, oom)) {
//                        if (itppl.isOnSameSide(tr, itr, oom)) {
//                            if (itqpl.isOnSameSide(tp, itp, oom)) {
//                                if (itqpl.isOnSameSide(tq, itp, oom)) {
//                                    if (itqpl.isOnSameSide(tr, itp, oom)) {
//                                        if (itqpl.isOnSameSide(tp, itp, oom)) {
//                                            if (itqpl.isOnSameSide(tq, itp, oom)) {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {
//                                                    return t;
//                                                } else {
//                                                    V3D_LineSegment ititpq = (V3D_LineSegment) it.getIntersection(t.pl.getPQ(), oom);
//                                                    return V3D_Triangle.getGeometry(ititpq, t.pl.getQR());
//                                                }
//                                            } else {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {
////                                                    V3D_LineSegment ititpq = (V3D_LineSegment) it.getIntersection(t.pl.getPQ(), oom);
////                                                    return V3D_Triangle.getGeometry(ititpq, t.pl.getQR());
//                                                    //?
//                                                } else {
//                                                    //?
//                                                }
//                                            }
//                                        } else {
//                                        }
//                                    } else {
//                                    }
//                                } else {
//                                }
//                            } else {
//                            }
//                        } else {
//                        }
//                    } else {
//                    }
//                } else {
//                    if (itppl.isOnSameSide(tq, itr, oom)) {
//                        if (itppl.isOnSameSide(tr, itr, oom)) {
//                            if (itqpl.isOnSameSide(tp, itp, oom)) {
//                                if (itqpl.isOnSameSide(tq, itp, oom)) {
//                                    if (itqpl.isOnSameSide(tr, itp, oom)) {
//                                        if (itqpl.isOnSameSide(tp, itp, oom)) {
//                                            if (itqpl.isOnSameSide(tq, itp, oom)) {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {
//                                                    return t;
//                                                } else {
//                                                    V3D_LineSegment ititpq = (V3D_LineSegment) it.getIntersection(t.pl.getPQ(), oom);
//                                                    return V3D_Triangle.getGeometry(ititpq, t.pl.getQR());
//                                                }
//                                            } else {
//                                                if (itqpl.isOnSameSide(tr, itp, oom)) {
////                                                    V3D_LineSegment ititpq = (V3D_LineSegment) it.getIntersection(t.pl.getPQ(), oom);
////                                                    return V3D_Triangle.getGeometry(ititpq, t.pl.getQR());
//                                                    //?
//                                                } else {
//                                                    //?
//                                                }
//                                            }
//                                        } else {
//                                        }
//                                    } else {
//                                    }
//                                } else {
//                                }
//                            } else {
//                            }
//                        } else {
//                        }
//                    } else {
//                        //...  return it;
//                    }
//                }
//                throw new UnsupportedOperationException();
            } else {
                /**
                 * Quadrilateral.
                 */
                V3D_ConvexHullCoplanar ic = (V3D_ConvexHullCoplanar) i;
                return ic.clip(t, t.getCentroid(oom, rm), oom, rm);
//                /**
//                 * If all the points of t are within the planes of ic, then
//                 * return t. If any of lines of the t intersect ic, then further
//                 * intersections are needed to derive the final shape.
//                 */
//                throw new UnsupportedOperationException();
////                if () {
////
////                } else {
////                    //... return ic;
////                }
            }
        }
    }

    /**
     * Get the intersection with the rectangle {@code r}.
     *
     * @param r The rectangle to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Rectangle r, int oom, RoundingMode rm) {
        V3D_Triangle r_pqr = r.getPQR();
        V3D_Triangle r_rsp = r.getRSP();
        V3D_FiniteGeometry i_pqr = getIntersection(r_pqr, oom, rm);
        V3D_FiniteGeometry i_rsp = getIntersection(r_rsp, oom, rm);
        /**
         * The intersections will be null, a point, a line segment, a triangle,
         * a quadrilateral, a pentagon or a hexagon.
         *
         */
        if (i_pqr == null) {
            return i_rsp;
        } else if (i_pqr instanceof V3D_Point i_pqr_p) {
            if (i_rsp == null) {
                return i_pqr_p;
            } else {
                return i_rsp;
            }
        } else if (i_pqr instanceof V3D_LineSegment) {
            if (i_rsp == null) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_Point) {
                return i_pqr;
            } else {
                return i_rsp;
            }
        } else if (i_pqr instanceof V3D_Triangle i_pqrt) {
            if (i_rsp == null) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_Point) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_LineSegment) {
                return i_pqr;
            } else {
                if (i_rsp instanceof V3D_Triangle i_rspt) {
                    V3D_ConvexHullCoplanar ch = new V3D_ConvexHullCoplanar(oom,
                            rm, i_pqrt, i_rspt);
                    return ch.simplify(oom, rm);
                } else {
                    // i_rsp instanceof V3D_ConvexHullCoplanar
                    V3D_ConvexHullCoplanar ch = new V3D_ConvexHullCoplanar(oom,
                            rm, (V3D_ConvexHullCoplanar) i_rsp, i_pqrt);
                    return ch.simplify(oom, rm);
                }
            }
        } else {
            // i_pqr instanceof V3D_ConvexHullCoplanar
            if (i_rsp == null) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_Point) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_LineSegment) {
                return i_pqr;
            } else if (i_rsp instanceof V3D_Triangle i_rspt) {
                V3D_ConvexHullCoplanar ch = new V3D_ConvexHullCoplanar(oom, rm,
                        (V3D_ConvexHullCoplanar) i_pqr, i_rspt);
                return ch.simplify(oom, rm);
            } else {
                // i_rsp instanceof V3D_ConvexHullCoplanar
                V3D_ConvexHullCoplanar ch = new V3D_ConvexHullCoplanar(oom, rm,
                        (V3D_ConvexHullCoplanar) i_pqr,
                        (V3D_ConvexHullCoplanar) i_rsp);
                return ch.simplify(oom, rm);
            }
        }
    }

    @Override
    public V3D_Point[] getPoints() {
        V3D_Point[] re = new V3D_Point[4];
        re[0] = getP();
        re[1] = getQ();
        re[2] = getR();
        re[3] = getS();
        return re;
    }

    /**
     * Get the distance to {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code p}.
     */
    public BigDecimal getDistance(V3D_Point p, int oom, RoundingMode rm) {
        return Math_BigRational.toBigDecimal(new Math_BigRationalSqrt(
                getDistanceSquared(p, oom, rm), oom, rm).getSqrt(oom, rm), oom, 
                rm);
    }

    /**
     * Get the distance squared to {@code p}.
     *
     * @param pt A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The distance squared to {@code p}.
     */
    public BigRational getDistanceSquared(V3D_Point pt, int oom, 
            RoundingMode rm) {
        if (isIntersectedBy(pt, oom, rm)) {
            return BigRational.ZERO;
        } else {
            return BigRational.min(
                    getPqr(oom, rm).getDistanceSquared(pt, oom, rm),
                    getPsq(oom, rm).getDistanceSquared(pt, oom, rm),
                    getQsr(oom, rm).getDistanceSquared(pt, oom, rm),
                    getSpr(oom, rm).getDistanceSquared(pt, oom, rm));
        }
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public BigDecimal getDistance(V3D_Line l, int oom, RoundingMode rm) {
        return Math_BigRational.toBigDecimal(new Math_BigRationalSqrt(
                getDistanceSquared(l, oom, rm), oom, rm).getSqrt(oom, rm), oom, 
                rm);
    }

    /**
     * Get the minimum distance to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public BigDecimal getDistance(V3D_LineSegment l, int oom, RoundingMode rm) {
        return Math_BigRational.toBigDecimal(new Math_BigRationalSqrt(
                getDistanceSquared(l, oom, rm), oom, rm).getSqrt(oom, rm), oom, 
                rm);
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_LineSegment l, int oom, RoundingMode rm) {
        if (getIntersection(l, oom, rm) != null) {
            return BigRational.ZERO;
        } else {
            return BigRational.min(
                    getPqr(oom, rm).getDistanceSquared(l, oom, rm),
                    getPsq(oom, rm).getDistanceSquared(l, oom, rm),
                    getQsr(oom, rm).getDistanceSquared(l, oom, rm),
                    getSpr(oom, rm).getDistanceSquared(l, oom, rm));
        }
    }

    /**
     * Change {@link #offset} without changing the overall line.
     *
     * @param offset What {@link #offset} is set to.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     */
    public void setOffset(V3D_Vector offset, int oom, RoundingMode rm) {
        p = p.add(offset, oom, rm).subtract(this.offset, oom, rm);
        q = q.add(offset, oom, rm).subtract(this.offset, oom, rm);
        r = r.add(offset, oom, rm).subtract(this.offset, oom, rm);
        s = s.add(offset, oom, rm).subtract(this.offset, oom, rm);
        this.offset = offset;
    }

    @Override
    public V3D_Tetrahedron rotate(V3D_Ray ray, V3D_Vector uv, Math_Angle ma, 
            BigRational theta, int oom, RoundingMode rm) {
        return new V3D_Tetrahedron(
                getP().rotate(ray, uv, ma, theta, oom, rm),
                getQ().rotate(ray, uv, ma, theta, oom, rm),
                getR().rotate(ray, uv, ma, theta, oom, rm),
                getS().rotate(ray, uv, ma, theta, oom, rm), oom, rm);
    }

    /**
     * Get the intersection between the geometry and the tetrahedron {@code t}.
     *
     * @param t The tetrahedron to intersect with.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return The V3D_Geometry.
     */
    public V3D_FiniteGeometry getIntersection(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        if (!getEnvelope(oom).isIntersectedBy(t.getEnvelope(oom), oom)) {
            return null;
        }
        V3D_FiniteGeometry pqri = getIntersection(t.getPqr(oom, rm), oom, rm);
        if (pqri == null) {
            V3D_FiniteGeometry psqi = getIntersection(t.getPsq(oom, rm), oom, rm);
            if (psqi == null) {
                V3D_FiniteGeometry qsri = getIntersection(t.getQsr(oom, rm), oom, rm);
                if (qsri == null) {
                    V3D_FiniteGeometry spri = getIntersection(t.getSpr(oom, rm), oom, rm);
                    if (spri == null) {
                        V3D_FiniteGeometry tpqri = t.getIntersection(getPqr(oom, rm), oom, rm);
                        if (tpqri == null) {
                            V3D_FiniteGeometry tpsqi = t.getIntersection(getPsq(oom, rm), oom, rm);
                            if (tpsqi == null) {
                                V3D_FiniteGeometry tqsri = t.getIntersection(getQsr(oom, rm), oom, rm);
                                if (tqsri == null) {
                                    V3D_FiniteGeometry tspri = t.getIntersection(getSpr(oom, rm), oom, rm);
                                    if (tspri == null) {
                                        if (isIntersectedBy(t.getP(), oom, rm)
                                                && isIntersectedBy(t.getQ(), oom, rm)
                                                && isIntersectedBy(t.getR(), oom, rm)
                                                && isIntersectedBy(t.getS(), oom, rm)) {
                                            return t;
                                        } else {
                                            if (t.isIntersectedBy(getP(), oom, rm)
                                                    && t.isIntersectedBy(getQ(), oom, rm)
                                                    && t.isIntersectedBy(getR(), oom, rm)
                                                    && t.isIntersectedBy(getS(), oom, rm)) {
                                                return this;
                                            } else {
                                                return null;
                                            }
                                        }
                                    } else {
                                        // Has to be a point.
                                        return tspri;
                                    }
                                } else if (tqsri instanceof V3D_Point) {
                                    return tqsri;
                                } else if (tqsri instanceof V3D_LineSegment) {
                                    return tqsri;
                                } else if (tqsri instanceof V3D_Triangle) {
                                    /**
                                     * Triangle: Find which point is on the
                                     * other side of the triangle plane. In this
                                     * case, this is the only point of
                                     *
                                     */
                                } else {
                                    // V3D_TriangleCoplanar
                                }
                            } else if (tpsqi instanceof V3D_Point) {
                                return tpsqi;
                            } else if (tpsqi instanceof V3D_LineSegment) {
                                return tpsqi;
                            } else if (tpsqi instanceof V3D_Triangle) {
                                /**
                                 * Triangle: Find which point is on the other
                                 * side of the triangle plane. In this case,
                                 * this is the only point of
                                 *
                                 */
                            } else {
                                // V3D_TriangleCoplanar
                            }
                        }
                    }
                }
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get the minimum distance squared to {@code l}.
     *
     * @param l A line segment.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code l}.
     */
    public BigRational getDistanceSquared(V3D_Line l, int oom, RoundingMode rm) {
        if (getIntersection(l, oom, rm) != null) {
            return BigRational.ZERO;
        } else {
            return BigRational.min(
                    getPqr(oom, rm).getDistanceSquared(l, oom, rm),
                    getPsq(oom, rm).getDistanceSquared(l, oom, rm),
                    getQsr(oom, rm).getDistanceSquared(l, oom, rm),
                    getSpr(oom, rm).getDistanceSquared(l, oom, rm));
        }
    }

    /**
     * Get the minimum distance to {@code p}.
     *
     * @param p A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code p}.
     */
    public BigDecimal getDistance(V3D_Plane p, int oom, RoundingMode rm) {
        return Math_BigRational.toBigDecimal(new Math_BigRationalSqrt(
                getDistanceSquared(p, oom, rm), oom, rm).getSqrt(oom, rm), oom,
                rm);
    }

    /**
     * Get the minimum distance squared to {@code p}.
     *
     * @param p A plane.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance squared to {@code p}.
     */
    public BigRational getDistanceSquared(V3D_Plane p, int oom, RoundingMode rm) {
        if (getIntersection(p, oom, rm) != null) {
            return BigRational.ZERO;
        } else {
            return BigRational.min(
                    getPqr(oom, rm).getDistanceSquared(p, oom, rm),
                    getPsq(oom, rm).getDistanceSquared(p, oom, rm),
                    getQsr(oom, rm).getDistanceSquared(p, oom, rm),
                    getSpr(oom, rm).getDistanceSquared(p, oom, rm));
        }
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code t}.
     */
    public BigDecimal getDistance(V3D_Triangle t, int oom, RoundingMode rm) {
        return Math_BigRational.toBigDecimal(new Math_BigRationalSqrt(
                getDistanceSquared(t, oom, rm), oom, rm)
                .getSqrt(oom, rm), oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A triangle.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code t}.
     */
    public BigRational getDistanceSquared(V3D_Triangle t, int oom, 
            RoundingMode rm) {
        if (getIntersection(t, oom, rm) != null) {
            return BigRational.ZERO;
        } else {
            return BigRational.min(
                    getPqr(oom, rm).getDistanceSquared(t, oom, rm),
                    getPsq(oom, rm).getDistanceSquared(t, oom, rm),
                    getQsr(oom, rm).getDistanceSquared(t, oom, rm),
                    getSpr(oom, rm).getDistanceSquared(t, oom, rm));
        }
    }

    /**
     * Get the minimum distance to {@code r}.
     *
     * @param r A rectangle.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code t}.
     */
    public BigDecimal getDistance(V3D_Rectangle r, int oom, RoundingMode rm) {
        return new Math_BigRationalSqrt(getDistanceSquared(r, oom, rm), oom, rm)
                .toBigDecimal(oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code r}.
     *
     * @param r A rectangle.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code t}.
     */
    public BigRational getDistanceSquared(V3D_Rectangle r, int oom, RoundingMode rm) {
        BigRational pqrd = getDistanceSquared(r.getPQR(), oom, rm);
        if (pqrd.compareTo(BigRational.ZERO) == 0) {
            return pqrd;
        } else {
            return BigRational.min(pqrd,
                    getDistanceSquared(r.getRSP(), oom, rm));
        }
    }

    /**
     * Get the minimum distance to {@code t}.
     *
     * @param t A tetrahedron.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code t}.
     */
    public BigDecimal getDistance(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        return Math_BigRational.toBigDecimal(new Math_BigRationalSqrt(
                getDistanceSquared(t, oom, rm), oom, rm)
                .getSqrt(oom, rm), oom, rm);
    }

    /**
     * Get the minimum distance squared to {@code t}.
     *
     * @param t A tetrahedron.
     * @param oom The Order of Magnitude for the precision of the result.
     * @param rm The RoundingMode if rounding is needed.
     * @return The minimum distance to {@code t}.
     */
    public BigRational getDistanceSquared(V3D_Tetrahedron t, int oom, RoundingMode rm) {
        if (getIntersection(t, oom, rm) != null) {
            return BigRational.ZERO;
        } else {
            return BigRational.min(
                    t.getDistanceSquared(getPqr(oom, rm), oom, rm),
                    t.getDistanceSquared(getPsq(oom, rm), oom, rm),
                    t.getDistanceSquared(getQsr(oom, rm), oom, rm),
                    t.getDistanceSquared(getSpr(oom, rm), oom, rm));
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_Envelope aabb, int oom, RoundingMode rm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
