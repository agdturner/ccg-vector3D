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
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;

/**
 * An envelope contains all the extreme values with respect to the X, Y and Z
 * axes. It is an axis aligned bounding box, which may have length of zero in
 * any direction. For a point the envelope is essentially the point. The
 * envelope may also be a line. In any case it has:
 * <ul>
 * <li>a top ({@link #t}) aligned with {@link #yMax}</li>
 * <li>a bottom ({@link #b}) aligned with {@link #yMin}</li>
 * <li>a left ({@link #l}) aligned with {@link #xMin}</li>
 * <li>a right ({@link #r}) aligned with {@link #xMax}</li>
 * <li>a fore ({@link #f}) aligned with {@link #zMin}</li>
 * <li>a aft ({@link #a}) aligned with {@link #zMax}</li>
 * </ul>
 * The following depiction of a bounding box indicate the location of the
 * different faces and also gives an abbreviated name of each point that
 * reflects these. This points are not stored explicitly in an instance of the
 * class with these names, but for a normal envelope (which is not a point or a
 * line or a plane), there are these 8 points stored in the rectangles that
 * represent each face. {@code
 *
 *                                                          z
 *                                    y                   +
 *                                    +                   /
 *                                    |                  /
 *                                    |                 /
 *                                    |                /
 *                                    |
 *                                                  a
 *                                    t
 *                      lta_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ rta
 *                        /|                                /|
 *                       / |                               / |
 *                      /  |                              /  |
 *                     /   |                             /   |
 *                    /    |                            /    |
 *                   /     |                           /     |
 *                  /      |                          /      |
 *                 /       |                         /       |
 *                /        |                        /        |
 *               /         |                       /         |
 *          ltf /_ _ _ _ _ |_ _ _ _ _ _ _ _ _ _ _ /rtf       |
 *             |           |                     |           |
 *             |           |                     |           |
 *  - ----  l  |           |                     |           |  r  ---- + x
 *             |           |                     |           |
 *             |        lba|_ _ _ _ _ _ _ _ _ _ _|_ _ _ _ _ _|rba
 *             |           /                     |           /
 *             |          /                      |          /
 *             |         /                       |         /
 *             |        /                        |        /
 *             |       /                         |       /
 *             |      /                          |      /
 *             |     /                           |     /
 *             |    /                            |    /
 *             |   /                             |   /
 *             |  /                              |  /
 *             | /                               | /
 *          lbf|/_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ |/rbf
 *
 *                                     b
 *                      f
 *                                     |
 *                    /                |
 *                   /                 |
 *                  /                  |
 *                 -                   |
 *                                     -
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Envelope extends V3D_Geometry implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * The minimum x-coordinate.
     */
    private final BigRational xMin;

    /**
     * The maximum x-coordinate.
     */
    private final BigRational xMax;

    /**
     * The minimum y-coordinate.
     */
    private final BigRational yMin;

    /**
     * The maximum y-coordinate.
     */
    private final BigRational yMax;

    /**
     * The minimum z-coordinate.
     */
    private final BigRational zMin;

    /**
     * The maximum z-coordinate.
     */
    private final BigRational zMax;

    /**
     * The top face.
     */
    protected final Geometry t;

    /**
     * The left face.
     */
    protected final Geometry l;

    /**
     * The aft face.
     */
    protected final Geometry a;

    /**
     * The right face.
     */
    protected final Geometry r;

    /**
     * The fore face.
     */
    protected final Geometry f;

    /**
     * The bottom face.
     */
    protected final Geometry b;

    /**
     * @param e An envelope.
     */
    public V3D_Envelope(V3D_Envelope e) {
        xMin = e.xMin;
        xMax = e.xMax;
        yMin = e.yMin;
        yMax = e.yMax;
        zMin = e.zMin;
        zMax = e.zMax;
        f = e.f;
        l = e.l;
        a = e.a;
        r = e.r;
        t = e.t;
        b = e.b;
    }

    /**
     * @param points The points used to form the envelop.
     */
    public V3D_Envelope(V3D_Point... points) {
        int len = points.length;
        switch (len) {
            case 0:
                throw new RuntimeException("Cannot create envelope from an empty "
                        + "collection of points.");
            case 1:
                xMin = points[0].x;
                xMax = points[0].x;
                yMin = points[0].y;
                yMax = points[0].y;
                zMin = points[0].z;
                zMax = points[0].z;
                f = new Point(points[0]);
                l = f;
                a = f;
                r = f;
                t = f;
                b = f;
                break;
            default:
                BigRational xmin = points[0].x;
                BigRational xmax = points[0].x;
                BigRational ymin = points[0].y;
                BigRational ymax = points[0].y;
                BigRational zmin = points[0].z;
                BigRational zmax = points[0].z;
                for (int i = 1; i < points.length; i++) {
                    xmin = BigRational.min(xmin, points[i].x);
                    xmax = BigRational.max(xmax, points[i].x);
                    ymin = BigRational.min(ymin, points[i].y);
                    ymax = BigRational.max(ymax, points[i].y);
                    zmin = BigRational.min(zmin, points[i].z);
                    zmax = BigRational.max(zmax, points[i].z);
                }
                if (xmin.compareTo(xmax) == 0) {
                    if (ymin.compareTo(ymax) == 0) {
                        if (zmin.compareTo(zmax) == 0) {
                            f = new Point(xmin, ymin, zmin);
                            l = f;
                            a = f;
                            r = f;
                            t = f;
                            b = f;
                        } else {
                            Point fb = new Point(xmin, ymin, zmin);
                            Point ft = new Point(xmin, ymax, zmin);
                            f = new LineSegment(fb, ft);
                            l = f;
                            a = f;
                            r = f;
                            t = ft;
                            b = fb;
                        }
                    } else {
                        if (zmin.compareTo(zmax) == 0) {
                            Point fb = new Point(xmin, ymin, zmin);
                            Point lt = new Point(xmin, ymax, zmin);
                            f = new LineSegment(fb, lt);
                            l = f;
                            a = f;
                            r = f;
                            t = lt;
                            b = fb;
                        } else {
                            Point bf = new Point(xmin, ymin, zmin);
                            Point ba = new Point(xmin, ymin, zmax);
                            Point tf = new Point(xmin, ymax, zmin);
                            Point ta = new Point(xmin, ymax, zmax);
                            f = new LineSegment(bf, tf);
                            l = new Rectangle(ba, ta, tf, bf);
                            a = new LineSegment(ba, ta);
                            r = new Rectangle(bf, tf, ta, ba);
                            t = new LineSegment(tf, ta);
                            b = new LineSegment(ba, bf);
                        }
                    }
                } else {
                    if (ymin.compareTo(ymax) == 0) {
                        if (zmin.compareTo(zmax) == 0) {
                            Point lf = new Point(xmin, ymin, zmin);
                            Point rf = new Point(xmax, ymin, zmin);
                            f = new LineSegment(lf, rf);
                            l = lf;
                            a = new LineSegment(rf, lf);
                            r = rf;
                            t = f;
                            b = f;
                        } else {
                            Point lf = new Point(xmin, ymin, zmin);
                            Point la = new Point(xmin, ymin, zmax);
                            Point ra = new Point(xmax, ymin, zmax);
                            Point rf = new Point(xmax, ymin, zmin);
                            f = new LineSegment(lf, rf);
                            l = new LineSegment(la, lf);
                            a = new LineSegment(ra, la);
                            r = new LineSegment(rf, ra);
                            t = new Rectangle(lf, la, ra, rf);
                            b = new Rectangle(la, lf, rf, ra);
                        }
                    } else {
                        if (zmin.compareTo(zmax) == 0) {
                            Point lb = new Point(xmin, ymin, zmin);
                            Point lt = new Point(xmin, ymax, zmin);
                            Point rt = new Point(xmax, ymax, zmin);
                            Point rb = new Point(xmax, ymin, zmin);
                            f = new Rectangle(lb, lt, rt, rb);
                            l = new LineSegment(lb, lt);
                            a = new Rectangle(rb, rt, lt, lb);
                            r = new LineSegment(rb, rt);
                            t = new LineSegment(lt, rt);
                            b = new LineSegment(lb, rb);
                        } else {
                            Point lbf = new Point(xmin, ymin, zmin);
                            Point ltf = new Point(xmin, ymax, zmin);
                            Point rtf = new Point(xmax, ymax, zmin);
                            Point rbf = new Point(xmax, ymin, zmin);
                            Point lba = new Point(xmin, ymin, zmax);
                            Point lta = new Point(xmin, ymax, zmax);
                            Point rta = new Point(xmax, ymax, zmax);
                            Point rba = new Point(xmax, ymin, zmax);
                            f = new Rectangle(lbf, ltf, rtf, rbf);
                            l = new Rectangle(lba, lta, ltf, lbf);
                            a = new Rectangle(rba, rta, lta, lba);
                            r = new Rectangle(rbf, rtf, rta, rba);
                            t = new Rectangle(ltf, lta, rta, rtf);
                            b = new Rectangle(lba, lbf, rbf, rba);
                        }
                    }
                }
                this.xMin = xmin;
                this.xMax = xmax;
                this.yMin = ymin;
                this.yMax = ymax;
                this.zMin = zmin;
                this.zMax = zmax;
                break;
        }
    }

    /**
     * @param x The x-coordinate of a point.
     * @param y The y-coordinate of a point.
     * @param z The z-coordinate of a point.
     */
    public V3D_Envelope(BigRational x, BigRational y, BigRational z) {
        this(new V3D_Point(x, y, z));
    }

    /**
     * @param xMin What {@link xMin} is set to.
     * @param xMax What {@link xMax} is set to.
     * @param yMin What {@link yMin} is set to.
     * @param yMax What {@link yMax} is set to.
     * @param zMin What {@link zMin} is set to.
     * @param zMax What {@link zMax} is set to.
     */
    public V3D_Envelope(BigRational xMin, BigRational xMax, BigRational yMin,
            BigRational yMax, BigRational zMin, BigRational zMax) {
        this(new V3D_Point(xMin, yMin, zMin), new V3D_Point(xMax, yMax, zMax));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "(xMin=" + getxMin().toString() + ", xMax=" + getxMax().toString()
                + ", yMin=" + getyMin().toString() + ", yMax=" + getyMax().toString()
                + ", zMin=" + getzMin().toString() + ", zMax=" + getzMax().toString() + ")";
    }

    /**
     * @param v The vector to apply.
     * @return a new V3D_Envelope.
     */
    @Override
    public V3D_Envelope apply(V3D_Vector v) {
        V3D_Point lbf = new V3D_Point(xMin, yMin, zMin).apply(v);
        V3D_Point ltf = new V3D_Point(xMin, yMin, zMax).apply(v);
        V3D_Point rtf = new V3D_Point(xMax, yMin, zMax).apply(v);
        V3D_Point rbf = new V3D_Point(xMax, yMin, zMin).apply(v);
        V3D_Point lba = new V3D_Point(xMin, yMax, zMin).apply(v);
        V3D_Point lta = new V3D_Point(xMin, yMax, zMax).apply(v);
        V3D_Point rba = new V3D_Point(xMax, yMax, zMin).apply(v);
        V3D_Point rta = new V3D_Point(xMax, yMax, zMax).apply(v);
        return new V3D_Envelope(lbf, ltf, rtf, rbf, lba, lta, rba, rta);
    }

    /**
     * @param e The V3D_Envelope to union with this.
     * @return an Envelope which is {@code this} union {@code e}.
     */
    public V3D_Envelope union(V3D_Envelope e) {
        if (e.isContainedBy(this)) {
            return this;
        } else {
            return new V3D_Envelope(
                    BigRational.min(e.getxMin(), getxMin()),
                    BigRational.max(e.getxMax(), getxMax()),
                    BigRational.min(e.getyMin(), getyMin()),
                    BigRational.max(e.getyMax(), getyMax()),
                    BigRational.min(e.getzMin(), getzMin()),
                    BigRational.max(e.getzMax(), getzMax()));
        }
    }

    /**
     * If {@code e} touches, or overlaps then it intersects.
     *
     * @param e The Vector_Envelope2D to test for intersection.
     * @return {@code true} if this intersects with {@code e}.
     */
    public boolean isIntersectedBy(V3D_Envelope e) {
        if (e.getxMax().compareTo(getxMin()) != -1
                && e.getxMin().compareTo(getxMax()) != 1
                && getxMax().compareTo(e.getxMin()) != -1
                && getxMin().compareTo(e.getxMax()) != 1) {
            if (e.getyMax().compareTo(getyMin()) != -1
                    && e.getyMin().compareTo(getyMax()) != 1
                    && getyMax().compareTo(e.getyMin()) != -1
                    && getyMin().compareTo(e.getyMax()) != 1) {
                if (e.getzMax().compareTo(getzMin()) != -1
                        && e.getzMin().compareTo(getzMax()) != 1
                        && getzMax().compareTo(e.getzMin()) != -1
                        && getzMin().compareTo(e.getzMax()) != 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Containment includes the boundary. So anything in or on the boundary is
     * contained.
     *
     * @param e V3D_Envelope
     * @return if this is contained by {@code e}
     */
    public boolean isContainedBy(V3D_Envelope e) {
        return getxMax().compareTo(e.getxMax()) != 1
                && getxMin().compareTo(e.getxMin()) != -1
                && getyMax().compareTo(e.getyMax()) != 1
                && getyMin().compareTo(e.getyMin()) != -1
                && getzMax().compareTo(e.getzMax()) != 1
                && getzMin().compareTo(e.getzMin()) != -1;
    }

    /**
     * @param l Line segment to intersect with {@code this}.
     * @return either a point or line segment which is the intersection of
     * {@code l} and {@code this}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, boolean b) {
        V3D_Envelope le = l.getEnvelope();
        if (le.isIntersectedBy(this)) {
            return le.getIntersection(this).getIntersection(l);
        }
        return null;
    }

    /**
     * @param p The point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    @Override
    public boolean isIntersectedBy(V3D_Point p) {
        return isIntersectedBy(p.x, p.y, p.z);
    }

    /**
     * @param x The x-coordinate of the point to test for intersection.
     * @param y The y-coordinate of the point to test for intersection.
     * @param z The z-coordinate of the point to test for intersection.
     * @return {@code true} if this intersects with {@code p}
     */
    public boolean isIntersectedBy(BigRational x, BigRational y,
            BigRational z) {
        return x.compareTo(getxMin()) != -1 && x.compareTo(getxMax()) != 1
                && y.compareTo(getyMin()) != -1 && y.compareTo(getyMax()) != 1
                && z.compareTo(getzMin()) != -1 && z.compareTo(getzMax()) != 1;
    }

    /**
     * @param en The envelop to intersect.
     * @return {@code null} if there is no intersection; {@code en} if
     * {@code this.equals(en)}; otherwise returns the intersection.
     */
    public V3D_Envelope getIntersection(V3D_Envelope en) {
        if (this.equals(en)) {
            return en;
        }
        if (!this.isIntersectedBy(en)) {
            return null;
        }
        return new V3D_Envelope(
                BigRational.max(getxMin(), en.getxMin()),
                BigRational.min(getxMax(), en.getxMax()),
                BigRational.max(getyMin(), en.getyMin()),
                BigRational.min(getyMax(), en.getyMax()),
                BigRational.max(getzMin(), en.getzMin()),
                BigRational.min(getzMax(), en.getzMax()));
    }

    /**
     * Returns {@code null} if {@code this} does not intersect {@code l};
     * otherwise returns the intersection which is either a point or a line
     * segment.
     *
     * @param li The line to intersect.
     * @return {@code null} if there is no intersection; otherwise returns the
     * intersection.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line li) {
        if (t instanceof Point) {
            if (l instanceof Point) {
                V3D_Point p = new V3D_Point((Point) l);
                if (li.isIntersectedBy(p)) {
                    return p;
                }
            } else {
                V3D_LineSegment ls = new V3D_LineSegment((LineSegment) l);
                return ls.getIntersection(li);
            }
        }
        V3D_Geometry tli = new V3D_Rectangle((Rectangle) t).getIntersection(li);
        if (tli == null) {
            // Check l, a, r, f, b
            V3D_Geometry lli = new V3D_Rectangle((Rectangle) l).getIntersection(li);
            if (lli == null) {
                // Check a, r, f, b
                V3D_Geometry ali = new V3D_Rectangle((Rectangle) a).getIntersection(li);
                if (ali == null) {
                    // Check r, f, b
                    V3D_Geometry rli = new V3D_Rectangle((Rectangle) r).getIntersection(li);
                    if (rli == null) {
                        // Check f, b
                        V3D_Geometry fli = new V3D_Rectangle((Rectangle) f).getIntersection(li);
                        if (fli == null) {
                            // null intersection.
                            return null;
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            V3D_Point flip = (V3D_Point) fli;
                            V3D_Point blip = (V3D_Point) new V3D_Rectangle((Rectangle) b).getIntersection(li);
                            return getGeometry(flip, blip);
                        }
                    } else if (rli instanceof V3D_LineSegment) {
                        return rli;
                    } else {
                        V3D_Point rlip = (V3D_Point) rli;
                        // check for intersection with b
                        V3D_Geometry bli = new V3D_Rectangle((Rectangle) b).getIntersection(li);
                        if (bli == null) {
                            return rlip;
                        } else {
                            return getGeometry((V3D_Point) bli, rlip);
                        }
                    }
                } else if (ali instanceof V3D_LineSegment) {
                    return ali;
                } else {
                    // Check for intersection with r, f, b
                    V3D_Point alip = (V3D_Point) ali;
                    V3D_Geometry rli = new V3D_Rectangle((Rectangle) r).getIntersection(li);
                    if (rli == null) {
                        // Check f, b
                        V3D_Geometry fli = new V3D_Rectangle((Rectangle) f).getIntersection(li);
                        if (fli == null) {
                            // check for intersection with b
                            V3D_Geometry bli = new V3D_Rectangle((Rectangle) b).getIntersection(li);
                            if (bli == null) {
                                return alip;
                            } else if (bli instanceof V3D_LineSegment) {
                                return bli;
                            } else {
                                return getGeometry((V3D_Point) bli, alip);
                            }
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            return getGeometry((V3D_Point) fli, alip);
                        }
                    } else {
                        return getGeometry((V3D_Point) rli, alip);
                    }
                }
            } else if (lli instanceof V3D_LineSegment) {
                return lli;
            } else {
                V3D_Point llip = (V3D_Point) lli;
                // Check a, r, f, b
                V3D_Geometry ali = new V3D_Rectangle((Rectangle) a).getIntersection(li);
                if (ali == null) {
                    // Check r, f, b
                    V3D_Geometry rli = new V3D_Rectangle((Rectangle) r).getIntersection(li);
                    if (rli == null) {
                        // Check f, b
                        V3D_Geometry fli = new V3D_Rectangle((Rectangle) f).getIntersection(li);
                        if (fli == null) {
                            // Check b
                            V3D_Geometry bli = new V3D_Rectangle((Rectangle) b).getIntersection(li);
                            if (bli == null) {
                                return getGeometry(llip, (V3D_Point) bli);
                            } else if (bli instanceof V3D_LineSegment) {
                                return bli;
                            } else {
                                return getGeometry((V3D_Point) bli, llip);
                            }
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            // Check b
                            V3D_Geometry bli = new V3D_Rectangle((Rectangle) b).getIntersection(li);
                            if (bli == null) {
                                return getGeometry(llip, (V3D_Point) bli);
                            } else if (bli instanceof V3D_LineSegment) {
                                return bli;
                            } else {
                                return getGeometry((V3D_Point) bli, llip);
                            }
                        }
                    } else if (rli instanceof V3D_LineSegment) {
                        return rli;
                    } else {
                        return getGeometry((V3D_Point) rli, llip);
                    }
                } else if (ali instanceof V3D_LineSegment) {
                    return ali;
                } else {
                    // Check for intersection with r, f, b
                    V3D_Point alip = (V3D_Point) ali;
                    if (alip.equals(llip)) {
                        V3D_Geometry rli = new V3D_Rectangle((Rectangle) r).getIntersection(li);
                        if (rli == null) {
                            // Check f, b
                            V3D_Geometry fli = new V3D_Rectangle((Rectangle) f).getIntersection(li);
                            if (fli == null) {
                                // check for intersection with b
                                V3D_Geometry bli = new V3D_Rectangle((Rectangle) b).getIntersection(li);
                                if (bli == null) {
                                    return alip;
                                } else if (bli instanceof V3D_LineSegment) {
                                    return bli;
                                } else {
                                    return getGeometry((V3D_Point) bli, alip);
                                }
                            } else if (fli instanceof V3D_LineSegment) {
                                return fli;
                            } else {
                                return getGeometry((V3D_Point) fli, alip);
                            }
                        } else {
                            return getGeometry((V3D_Point) rli, llip);
                        }
                    } else {
                        return getGeometry(alip, llip);
                    }
                }
            }
        } else if (tli instanceof V3D_LineSegment) {
            return tli;
        } else {
            V3D_Point tlip = (V3D_Point) tli;
            // Check l, a, r, f, b
            V3D_Geometry lli = new V3D_Rectangle((Rectangle) l).getIntersection(li);
            if (lli == null) {
                // Check a, r, f, b
                V3D_Geometry ali = new V3D_Rectangle((Rectangle) a).getIntersection(li);
                if (ali == null) {
                    // Check r, f, b
                    V3D_Geometry rli = new V3D_Rectangle((Rectangle) r).getIntersection(li);
                    if (rli == null) {
                        // Check f, b
                        V3D_Geometry fli = new V3D_Rectangle((Rectangle) f).getIntersection(li);
                        if (fli == null) {
                            // Intersects b
                            V3D_Point blip = (V3D_Point) new V3D_Rectangle((Rectangle) b).getIntersection(li);
                            return getGeometry(tlip, blip);
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            // Check b
                            V3D_Geometry bli = new V3D_Rectangle((Rectangle) b).getIntersection(li);
                            if (bli == null) {
                                return getGeometry(tlip, (V3D_Point) bli);
                            } else if (bli instanceof V3D_LineSegment) {
                                return bli;
                            } else {
                                return getGeometry((V3D_Point) bli, tlip);
                            }
                        }
                    } else if (rli instanceof V3D_LineSegment) {
                        return rli;
                    } else {
                        V3D_Point rlip = (V3D_Point) rli;
                        // check for intersection with b
                        V3D_Geometry bli = new V3D_Rectangle((Rectangle) b).getIntersection(li);
                        if (bli == null) {
                            return rlip;
                        } else {
                            return getGeometry((V3D_Point) bli, rlip);
                        }
                    }
                } else if (ali instanceof V3D_LineSegment) {
                    return ali;
                } else {
                    // Check for intersection with r, f, b
                    V3D_Point alip = (V3D_Point) ali;
                    V3D_Geometry rli = new V3D_Rectangle((Rectangle) r).getIntersection(li);
                    if (rli == null) {
                        // Check f, b
                        V3D_Geometry fli = new V3D_Rectangle((Rectangle) f).getIntersection(li);
                        if (fli == null) {
                            // check for intersection with b
                            V3D_Geometry bli = new V3D_Rectangle((Rectangle) b).getIntersection(li);
                            if (bli == null) {
                                return alip;
                            } else if (bli instanceof V3D_LineSegment) {
                                return bli;
                            } else {
                                return getGeometry((V3D_Point) bli, alip);
                            }
                        } else if (fli instanceof V3D_LineSegment) {
                            return fli;
                        } else {
                            return getGeometry((V3D_Point) fli, alip);
                        }
                    } else {
                        V3D_Point rlip = (V3D_Point) rli;
                        if (rlip.equals(alip)) {
                            // Still more checking to do...
                            // Check f, b
                            V3D_Geometry fli = new V3D_Rectangle((Rectangle) f).getIntersection(li);
                            if (fli == null) {
                                // check for intersection with b
                                V3D_Geometry bli = new V3D_Rectangle((Rectangle) b).getIntersection(li);
                                if (bli == null) {
                                    return alip;
                                } else if (bli instanceof V3D_LineSegment) {
                                    return bli;
                                } else {
                                    return getGeometry((V3D_Point) bli, alip);
                                }
                            } else if (fli instanceof V3D_LineSegment) {
                                return fli;
                            } else {
                                return getGeometry((V3D_Point) fli, alip);
                            }
                        } else {
                            return getGeometry((V3D_Point) rli, alip);
                        }
                    }
                }
            } else if (lli instanceof V3D_LineSegment) {
                return lli;
            } else {
                // Still more checking to do...
                // intersection top and left could be at a corner and anyway need to check other faces...
                return getGeometry(tlip, (V3D_Point) lli);
            }
        }
    }

    private V3D_Geometry getGeometry(V3D_Point p, V3D_Point q) {
        if (p.equals(q)) {
            return p;
        } else {
            return new V3D_LineSegment(p, q);
        }
    }

    @Override
    public V3D_Envelope getEnvelope() {
        return this;
    }

    /**
     * Test for equality.
     *
     * @param g The V3D_Geometry to test for equality with this.
     * @return {@code true} iff this and e are equal.
     */
    @Override
    public boolean equals(V3D_Geometry g) {
        if (g instanceof V3D_Envelope) {
            return equals((V3D_Envelope) g);
        }
        return false;
    }

    /**
     * Test for equality.
     *
     * @param e The V3D_Envelope to test for equality with this.
     * @return {@code true} iff this and e are equal.
     */
    public boolean equals(V3D_Envelope e) {
        return this.getxMin().compareTo(e.getxMin()) == 0
                && this.getxMax().compareTo(e.getxMax()) == 0
                && this.getyMin().compareTo(e.getyMin()) == 0
                && this.getyMax().compareTo(e.getyMax()) == 0
                && this.getzMin().compareTo(e.getzMin()) == 0
                && this.getzMax().compareTo(e.getzMax()) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Envelope) {
            return equals((V3D_Envelope) o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.getxMin());
        hash = 43 * hash + Objects.hashCode(this.getxMax());
        hash = 43 * hash + Objects.hashCode(this.getyMin());
        hash = 43 * hash + Objects.hashCode(this.getyMax());
        hash = 43 * hash + Objects.hashCode(this.getzMin());
        hash = 43 * hash + Objects.hashCode(this.getzMax());
        return hash;
    }

    /**
     * @return the xMin
     */
    public BigRational getxMin() {
        return xMin;
    }

    /**
     * @return the xMax
     */
    public BigRational getxMax() {
        return xMax;
    }

    /**
     * @return the yMin
     */
    public BigRational getyMin() {
        return yMin;
    }

    /**
     * @return the yMax
     */
    public BigRational getyMax() {
        return yMax;
    }

    /**
     * @return the zMin
     */
    public BigRational getzMin() {
        return zMin;
    }

    /**
     * @return the zMax
     */
    public BigRational getzMax() {
        return zMax;
    }

    /**
     * Each part of this testing could be done simultaneously which might speed
     * things up.
     *
     * @param li The line to test for intersection.
     * @return {@code tres} iff {@code this} is intersected by {@code li}.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line li) {
        if (new V3D_Rectangle((Rectangle) t).isIntersectedBy(li)) {
            return true;
        } else if (new V3D_Rectangle((Rectangle) l).isIntersectedBy(li)) {
            return true;
        } else if (new V3D_Rectangle((Rectangle) a).isIntersectedBy(li)) {
            return true;
        } else if (new V3D_Rectangle((Rectangle) r).isIntersectedBy(li)) {
            return true;
        } else {
            return new V3D_Rectangle((Rectangle) f).isIntersectedBy(li);
        }
    }

    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, boolean b) {
        V3D_Envelope le = l.getEnvelope();
        if (le.isIntersectedBy(this)) {
            if (this.isIntersectedBy(l.p) || this.isIntersectedBy(l.q)) {
                return true;
            }
            if (new V3D_Rectangle((Rectangle) this.b).isIntersectedBy(l)) {
                return true;
            }
            return isIntersectedBy(l);
        }
        return false;
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l) {
        return isIntersectedBy(l);
    }

    /**
     * A point within the Envelope currently returns a distance of zero. This
     * could be changed in future to provide a negative distance which would be
     * the distance inside the envelope. To calculate the distance outside the
     * envelope, there are a number of different cases. The first cases are when
     * the closest point on the Envelope to the point {@code p} is one of the
     * corners. The next cases are when it is a point on one of the edges of
     * {@link #t}, {@link #b}, {@link #l}, {@link #r}, {@link #f}. The next
     * cases are when it is a point on one of the faces of {@link #t},
     * {@link #b}, {@link #l}, {@link #r}, {@link #f}. Depending on the values
     * and the oom given. The distance may appear to be zero when it is in fact
     * greater than zero. To test if the distance is zero use
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Point)}.
     *
     * @param p The point to find the distance to/from.
     * @param oom The Order of Magnitude for the result precision.
     * @return The approximate or exact distance at the given {@code oom}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        if (this.isIntersectedBy(p)) {
            return BigDecimal.ZERO;
        }
        // Special case where Envelope is infinitesimally small.
        if (l instanceof Point && t instanceof Point) {
            return ((Point) l).getDistance(new Point(p)).toBigDecimal(oom);
        }
        int xcmin = p.x.compareTo(xMin);
        int ycmin = p.y.compareTo(yMin);
        int zcmin = p.z.compareTo(zMin);
        if (xcmin == -1) {
            if (ycmin == -1) {
                if (zcmin == -1) {
                    // lbf
                    if (f instanceof Rectangle) {
                        return ((Rectangle) f).p.getDistance(new Point(p)).toBigDecimal(oom);
                    } else if (f instanceof LineSegment) {
                        return ((LineSegment) f).p.getDistance(new Point(p)).toBigDecimal(oom);
                    } else {
                        return ((Point) f).getDistance(new Point(p)).toBigDecimal(oom);
                    }
                } else {
                    int zcmax = p.z.compareTo(zMax);
                    if (zcmax == 1) {
                        // bla
                        if (l instanceof Rectangle) {
                            return ((Rectangle) l).p.getDistance(new Point(p)).toBigDecimal(oom);
                        } else if (l instanceof LineSegment) {
                            return ((LineSegment) l).p.getDistance(new Point(p)).toBigDecimal(oom);
                        } else {
                            return ((Point) l).getDistance(new Point(p)).toBigDecimal(oom);
                        }
                    } else {
                        // lba - lbf
                        if (l instanceof Rectangle) {
                            return new Line(((Rectangle) l).b).getDistance(new Point(p), oom);
                        } else if (l instanceof LineSegment) {
                            return ((Line) l).getDistance(new Point(p), oom);
                        } else {
                            return ((Point) l).getDistance(new Point(p), oom);
                        }
                    }
                }
            } else {
                int ycmax = p.y.compareTo(yMax);
                if (ycmax == 1) {
                    if (zcmin == -1) {
                        // ltf
                        if (f instanceof Rectangle) {
                            return ((Rectangle) f).q.getDistance(new Point(p), oom);
                        } else if (f instanceof LineSegment) {
                            return ((LineSegment) f).p.getDistance(new Point(p), oom);
                        } else {
                            return ((Point) f).getDistance(new Point(p), oom);
                        }
                    } else {
                        int zcmax = p.z.compareTo(zMax);
                        if (zcmax == 1) {
                            // lta
                            if (l instanceof Rectangle) {
                                return ((Rectangle) l).q.getDistance(new Point(p), oom);
                            } else if (l instanceof LineSegment) {
                                return ((LineSegment) l).p.getDistance(new Point(p), oom);
                            } else {
                                return ((Point) l).getDistance(new Point(p), oom);
                            }
                        } else {
                            // lta - ltf
                            if (l instanceof Rectangle) {
                                return new Line(((Rectangle) l).t).getDistance(new Point(p), oom);
                            } else if (l instanceof LineSegment) {
                                return ((Line) l).getDistance(new Point(p), oom);
                            } else {
                                return ((Point) l).getDistance(new Point(p), oom);
                            }
                        }
                    }
                } else {
                    if (zcmin == -1) {
                        // lbf - ltf
                        if (l instanceof Rectangle) {
                            return new Line(((Rectangle) l).ri).getDistance(new Point(p), oom);
                        } else if (l instanceof LineSegment) {
                            return ((Line) l).getDistance(new Point(p), oom);
                        } else {
                            return ((Point) l).getDistance(new Point(p), oom);
                        }
                    } else {
                        int zcmax = p.z.compareTo(zMax);
                        if (zcmax == 1) {
                            // lba - lta
                            if (l instanceof Rectangle) {
                                return new Line(((Rectangle) l).l).getDistance(new Point(p), oom);
                            } else if (l instanceof LineSegment) {
                                return ((Line) l).getDistance(new Point(p), oom);
                            } else {
                                return ((Point) l).getDistance(new Point(p), oom);
                            }
                        } else {
                            // lba - lta - ltf - lbf
                            if (l instanceof Rectangle) {
                                return new Plane((Rectangle) l).getDistance(new Point(p), oom);
                            } else if (l instanceof LineSegment) {
                                return ((Line) l).getDistance(new Point(p), oom);
                            } else {
                                return ((Point) l).getDistance(new Point(p), oom);
                            }
                        }
                    }
                }
            }
        } else {
            int xcmax = p.x.compareTo(xMax);
            if (xcmax == 1) {
                if (ycmin == -1) {
                    if (zcmin == -1) {
                        // rbf
                        if (f instanceof Rectangle) {
                            return ((Rectangle) f).s.getDistance(new Point(p), oom);
                        } else if (f instanceof LineSegment) {
                            return ((LineSegment) f).q.getDistance(new Point(p), oom);
                        } else {
                            return ((Point) f).getDistance(new Point(p), oom);
                        }
                    } else {
                        int zcmax = p.z.compareTo(zMax);
                        if (zcmax == 1) {
                            // rba
                            if (a instanceof Rectangle) {
                                return ((Rectangle) a).p.getDistance(new Point(p), oom);
                            } else if (a instanceof LineSegment) {
                                return ((LineSegment) a).p.getDistance(new Point(p), oom);
                            } else {
                                return ((Point) a).getDistance(new Point(p), oom);
                            }
                        } else {
                            // rbf - rba
                            if (r instanceof Rectangle) {
                                return new Line(((Rectangle) r).b).getDistance(new Point(p), oom);
                            } else if (r instanceof LineSegment) {
                                return ((Line) r).getDistance(new Point(p), oom);
                            } else {
                                return ((Point) r).getDistance(new Point(p), oom);
                            }
                        }
                    }
                } else {
                    int ycmax = p.y.compareTo(yMax);
                    if (ycmax == 1) {
                        if (zcmin == -1) {
                            // rtf
                            if (f instanceof Rectangle) {
                                return ((Rectangle) f).r.getDistance(new Point(p), oom);
                            } else if (f instanceof LineSegment) {
                                return ((LineSegment) f).q.getDistance(new Point(p), oom);
                            } else {
                                return ((Point) f).getDistance(new Point(p), oom);
                            }
                        } else {
                            int zcmax = p.z.compareTo(zMax);
                            if (zcmax == 1) {
                                // rta
                                if (a instanceof Rectangle) {
                                    return ((Rectangle) a).q.getDistance(new Point(p), oom);
                                } else if (a instanceof LineSegment) {
                                    return ((LineSegment) a).p.getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) a).getDistance(new Point(p), oom);
                                }
                            } else {
                                // rtf - rta
                                if (r instanceof Rectangle) {
                                    return new Line(((Rectangle) r).t).getDistance(new Point(p), oom);
                                } else if (r instanceof LineSegment) {
                                    return ((Line) r).getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) r).getDistance(new Point(p), oom);
                                }
                            }
                        }
                    } else {
                        if (zcmin == -1) {
                            // rbf - rtf
                            if (f instanceof Rectangle) {
                                return new Line(((Rectangle) f).ri).getDistance(new Point(p), oom);
                            } else if (f instanceof LineSegment) {
                                return ((Line) f).getDistance(new Point(p), oom);
                            } else {
                                return ((Point) f).getDistance(new Point(p), oom);
                            }
                        } else {
                            int zcmax = p.z.compareTo(zMax);
                            if (zcmax == 1) {
                                // rba - rta
                                if (a instanceof Rectangle) {
                                    return new Line(((Rectangle) a).l).getDistance(new Point(p), oom);
                                } else if (a instanceof LineSegment) {
                                    return ((Line) a).getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) a).getDistance(new Point(p), oom);
                                }
                            } else {
                                // rbf - rtf - rta - rba
                                if (r instanceof Rectangle) {
                                    return new Plane((Rectangle) r).getDistance(new Point(p), oom);
                                } else if (r instanceof LineSegment) {
                                    return ((Line) r).getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) r).getDistance(new Point(p), oom);
                                }
                            }
                        }
                    }
                }
            } else {
                if (ycmin == -1) {
                    if (zcmin == -1) {
                        // lbf - rbf
                        if (f instanceof Rectangle) {
                            return new Line(((Rectangle) f).b).getDistance(new Point(p), oom);
                        } else if (f instanceof LineSegment) {
                            return ((Line) f).getDistance(new Point(p), oom);
                        } else {
                            return ((Point) f).getDistance(new Point(p), oom);
                        }
                    } else {
                        int zcmax = p.z.compareTo(zMax);
                        if (zcmax == 1) {
                            // rba - lba
                            if (a instanceof Rectangle) {
                                return new Line(((Rectangle) a).b).getDistance(new Point(p), oom);
                            } else if (a instanceof LineSegment) {
                                return ((Line) a).getDistance(new Point(p), oom);
                            } else {
                                return ((Point) a).getDistance(new Point(p), oom);
                            }
                        } else {
                            // lba - lbf - rbf - rba
                            if (b instanceof Rectangle) {
                                return new Plane((Rectangle) b).getDistance(new Point(p), oom);
                            } else if (b instanceof LineSegment) {
                                return ((Line) b).getDistance(new Point(p), oom);
                            } else {
                                return ((Point) b).getDistance(new Point(p), oom);
                            }
                        }
                    }
                } else {
                    int ycmax = p.y.compareTo(yMax);
                    if (ycmax == 1) {
                        if (zcmin == -1) {
                            // ltf - rtf
                            if (f instanceof Rectangle) {
                                return ((Rectangle) f).t.getDistance(new Point(p), oom);
                            } else if (f instanceof LineSegment) {
                                return ((LineSegment) f).q.getDistance(new Point(p), oom);
                            } else {
                                return ((Point) f).getDistance(new Point(p), oom);
                            }
                        } else {
                            int zcmax = p.z.compareTo(zMax);
                            if (zcmax == 1) {
                                // rta - lta
                                if (a instanceof Rectangle) {
                                    return new Line(((Rectangle) a).t).getDistance(new Point(p), oom);
                                } else if (a instanceof LineSegment) {
                                    return ((Line) a).getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) a).getDistance(new Point(p), oom);
                                }
                            } else {
                                // ltf - lta - rta - rtf
                                if (t instanceof Rectangle) {
                                    return new Plane((Rectangle) t).getDistance(new Point(p), oom);
                                } else if (r instanceof LineSegment) {
                                    return ((Line) t).getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) t).getDistance(new Point(p), oom);
                                }
                            }
                        }
                    } else {
                        if (zcmin == -1) {
                            // lbf - ltf - rtf - rbf
                            if (f instanceof Rectangle) {
                                return new Plane((Rectangle) f).getDistance(new Point(p), oom);
                            } else if (f instanceof LineSegment) {
                                return ((Line) f).getDistance(new Point(p), oom);
                            } else {
                                return ((Point) f).getDistance(new Point(p), oom);
                            }
                        } else {
                            int zcmax = p.z.compareTo(zMax);
                            if (zcmax == 1) {
                                // rba - rta - lta - lba
                                if (a instanceof Rectangle) {
                                    return new Plane((Rectangle) a).getDistance(new Point(p), oom);
                                } else if (a instanceof LineSegment) {
                                    return ((Line) a).getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) a).getDistance(new Point(p), oom);
                                }
                            } else {
                                // lba - lbf - rbf - rba
                                if (r instanceof Rectangle) {
                                    return new Plane((Rectangle) b).getDistance(new Point(p), oom);
                                } else if (r instanceof LineSegment) {
                                    return ((LineSegment) b).getDistance(new Point(p), oom);
                                } else {
                                    return ((Point) b).getDistance(new Point(p), oom);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Abstract Geometry class for geometries aligning with axes.
     */
    public abstract class Geometry implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * Created a new Geometry.
         */
        public Geometry() {
        }
    }

    /**
     * A basic Point class.
     */
    public class Point extends Geometry {

        private static final long serialVersionUID = 1L;

        /**
         * The x coordinate.
         */
        public BigRational x;

        /**
         * The y coordinate.
         */
        public BigRational y;

        /**
         * The z coordinate.
         */
        public BigRational z;

        /**
         * Create a new instance from {@code p}.
         *
         * @param p The point to duplicate
         */
        public Point(V3D_Point p) {
            x = p.x;
            y = p.y;
            z = p.z;
        }

        /**
         * Create a new instance from {@code x}, {@code y}, {@code z}.
         *
         * @param x What {@link #x} is set to.
         * @param y What {@link #y} is set to.
         * @param z What {@link #z} is set to.
         */
        public Point(BigRational x, BigRational y, BigRational z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * Create a new instance from {@code v}.
         *
         * @param v The vector.
         */
        public Point(V3D_Vector v) {
            x = v.dx;
            y = v.dy;
            z = v.dz;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "(x=" + x.toString()
                    + ", y=" + y.toString() + ", z=" + z.toString() + ")";
        }

        /**
         * Get the distance between this and {@code p}.
         *
         * @param p A point.
         * @param oom The Order of Magnitude for the precision of the result.
         * @return The distance from {@code p} to this.
         */
        public BigDecimal getDistance(Point p, int oom) {
            if (this.equals(p)) {
                return BigDecimal.ZERO;
            }
            return Math_BigDecimal.sqrt(getDistanceSquared(p).toBigDecimal(),
                    oom, RoundingMode.HALF_UP);
        }

        /**
         * Get the distance between this and {@code p}.
         *
         * @param p A point.
         * @return The distance from {@code p} to this.
         */
        public Math_BigRationalSqrt getDistance(Point p) {
            if (this.equals(p)) {
                return Math_BigRationalSqrt.ZERO;
            }
            return new Math_BigRationalSqrt(getDistanceSquared(p));
        }

        /**
         * Get the distance squared between this and {@code p}.
         *
         * @param p A point.
         * @return The distance squared from {@code p} to this.
         */
        public BigRational getDistanceSquared(Point p) {
            BigRational dx = this.x.subtract(p.x);
            BigRational dy = this.y.subtract(p.y);
            BigRational dz = this.z.subtract(p.z);
            return dx.pow(2).add(dy.pow(2)).add(dz.pow(2));
        }

    }

    /**
     * A line that aligns with the axes (a reticule).
     */
    public class Line extends Geometry {

        private static final long serialVersionUID = 1L;

        /**
         * A point defining the line.
         */
        public final Point p;

        /**
         * A point defining the line.
         */
        public final Point q;

        /**
         * The direction vector from {@link #p} in the direction of {@link #q}.
         */
        public final V3D_Vector v;

        /**
         * {@code p} should not be equal to {@code q}. If unsure use
         * {@link #V3D_Line(V3D_Point, V3D_Point, boolean)}.
         *
         * @param p What {@link #p} is set to.
         * @param q What {@link #q} is set to.
         */
        public Line(Point p, Point q) {
            this.p = p;
            this.q = q;
            v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                    q.z.subtract(p.z));
        }

        /**
         * Create a new instance from {@code l}
         *
         * @param l Line to create from.
         */
        public Line(Line l) {
            this.p = l.p;
            this.q = l.q;
            v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                    q.z.subtract(p.z));
        }

        /**
         * @param l The line to test this with to see if they are parallel.
         * @return {@code true} If this and {@code l} are parallel.
         */
        public boolean isParallel(Line l) {
            return v.isScalarMultiple(l.v);
        }

        /**
         * @param pt A point to test for intersection.
         * @return {@code true} if p is on the line.
         */
        public boolean isIntersectedBy(Point pt) {
            V3D_Vector ppt = new V3D_Vector(pt.x.subtract(p.x),
                    pt.y.subtract(p.y), pt.z.subtract(p.z));
            V3D_Vector cp = v.getCrossProduct(ppt);
            return cp.dx.isZero() && cp.dy.isZero() && cp.dz.isZero();
        }

        /**
         * This computes the intersection and tests if it is {@code null}
         *
         * @param l The line to test if it isIntersectedBy with this.
         * @return {@code true} If this and {@code l} intersect.
         */
        public boolean isIntersectedBy(Line l) {
            return getIntersection(l) != null;
        }

        /**
         * Get the intersection between two lines.
         *
         * @param l Another line.
         * @return The intersection between two lines or {@code null}.
         */
        public Geometry getIntersection(Line l) {
            // If lines are coincident return line.
            if (this.isParallel(l)) {
                if (l.isIntersectedBy(p)) {
                    return l;
                } else {
                    return null;
                }
            }
            if (l.v.dx.compareTo(BigRational.ZERO) == 0) {
                if (l.v.dy.compareTo(BigRational.ZERO) == 0) {
                    if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                        if (isIntersectedBy(l.p)) {
                            return l.p;
                        }
                    }
                }
                if (v.dx.compareTo(BigRational.ZERO) == 0) {
                    //Point p = new Point(l.p.x, );
                }
            } else {

            }
            return null;
        }

        /**
         * https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
         *
         * @param p A point for which the minimum distance from {@code this} is
         * returned.
         *
         * @param oom The Order of Magnitude for the precision of the result.
         * @return The minimum distance between this and {@code p}.
         */
        public BigDecimal getDistance(Point p, int oom) {
            V3D_Vector pv = new V3D_Vector(this.p, p);
            V3D_Vector vu = v.getUnitVector(oom - 2);
            return p.getDistance(new Point(vu.multiply(pv.getDotProduct(vu))
                    .add(new V3D_Vector(this.p))), oom);
        }

        /**
         *
         * @param l A line for which the minimum distance from {@code this} is
         * returned.
         * @param oom The Order of Magnitude for the precision of the result.
         * @return The minimum distance between this and {@code l}.
         */
        public BigDecimal getDistance(Line l, int oom) {
            if (isParallel(l)) {
                return l.getDistance(p, oom);
            } else {
                /**
                 * Calculate the direction vector of the line connecting the
                 * closest points by computing the cross product.
                 */
                V3D_Vector cp = v.getCrossProduct(l.v);
                /**
                 * Calculate the delta from {@link #p} and l.p
                 */
                V3D_Vector delta = new V3D_Vector(p).subtract(new V3D_Vector(l.p));
                BigRational m = BigRational.valueOf(cp.getMagnitude(oom - 2));
                // d = cp.(delta)/m
                BigRational dp = cp.getDotProduct(delta);
                // m should only be zero if the lines are parallel.
                BigRational d = dp.divide(m);
                return Math_BigDecimal.round(d.toBigDecimal(), oom);
            }
        }
    }

    /**
     * An axis aligned line segment.
     */
    public class LineSegment extends Line {

        private static final long serialVersionUID = 1L;

        /**
         * @param p What {@link #p} is set to.
         * @param q What {@link #q} is set to.
         */
        public LineSegment(Point p, Point q) {
            super(p, q);
        }

        /**
         * @param p A point to test for intersection within the specified
         * tolerance.
         * @return true if p is within t of this given scale.
         */
        @Override
        public boolean isIntersectedBy(Point p) {
            if (super.isIntersectedBy(p)) {
                Math_BigRationalSqrt a = p.getDistance(this.p);
                if (a.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt b = p.getDistance(this.q);
                if (b.getX().isZero()) {
                    return true;
                }
                Math_BigRationalSqrt l = this.p.getDistance(this.q);
                if (a.add(b).compareTo(l) != 1) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Intersects {@code this} with {@code l}. If they are equivalent then
         * return {@code this}. If they overlap in a line return the part that
         * overlaps (the order of points is not defined). If they intersect at a
         * point, the point is returned. {@code null} is returned if the two
         * line segments do not intersect.
         *
         * @param l The line to get intersection with this.
         * @return The intersection between {@code this} and {@code l}.
         */
        @Override
        public Geometry getIntersection(Line l) {
            // Check if infinite lines intersect.
            Geometry i = l.getIntersection(new Line(this));
            if (i == null) {
                // There is no intersection.
                return i;
            }
            /**
             * If infinite lines intersects at a point, then check this point is
             * on this.
             */
            if (i instanceof Point) {
                if (isIntersectedBy((Point) i)) {
                    return i;
                } else {
                    return null;
                }
            } else if (i instanceof Line) {
                // If the lines are the same, then return this. 
                return this;
            } else {
                // There is no intersection.
                return null;
            }
        }
    }

    /**
     * A plane aligning with the axes.
     */
    public class Plane extends Geometry {

        private static final long serialVersionUID = 1L;

        /**
         * One of the points that defines the plane.
         */
        protected final Point p;

        /**
         * One of the points that defines the plane.
         */
        protected final Point q;

        /**
         * One of the points that defines the plane.
         */
        protected final Point r;

        /**
         * The vector representing the move from {@link #p} to {@link #q}.
         */
        protected final V3D_Vector pq;

        /**
         * The vector representing the move from {@link #q} to {@link #r}.
         */
        protected final V3D_Vector qr;

        /**
         * The normal vector. (This is perpendicular to the plane and it's
         * direction is given by order in which the two vectors {@link #pq} and
         * {@link #qr} are used in a cross product calculation when the plane is
         * constructed.
         */
        protected final V3D_Vector n;

        /**
         * Create a new instance.
         *
         * @param p The plane used to create this.
         */
        public Plane(Plane p) {
            this(p.p, p.q, p.r, p.pq, p.qr, p.n);
        }

        /**
         * Create a new instance.
         *
         * @param p What {@link #p} is set to.
         * @param q What {@link #q} is set to.
         * @param r What {@link #r} is set to.
         * @param pq What {@link #pq} is set to.
         * @param qr What {@link #qr} is set to.
         * @param n What {@link #n} is set to.
         */
        public Plane(Point p, Point q, Point r, V3D_Vector pq, V3D_Vector qr,
                V3D_Vector n) {
            this.p = p;
            this.q = q;
            this.r = r;
            this.pq = pq;
            this.qr = qr;
            this.n = n;
        }

        /**
         * Create a new instance.
         *
         * @param p What {@link #p} is set to.
         * @param q What {@link #q} is set to.
         * @param r What {@link #r} is set to.
         */
        public Plane(Point p, Point q, Point r) {
            this(p, q, r, new V3D_Vector(p, q), new V3D_Vector(q, r),
                    new V3D_Vector(p, q).getCrossProduct(new V3D_Vector(q, r)));
        }

        /**
         * @param l The line to test if it is parallel to this.
         * @return {@code true} if {@code this} is parallel to {@code l}.
         */
        public boolean isParallel(Line l) {
            return n.getDotProduct(l.v).isZero();
        }

        /**
         * @param l The line to test if it is on the plane.
         * @return {@code true} If {@code pt} is on the plane.
         */
        public boolean isOnPlane(Line l) {
            return isIntersectedBy(l.p) && isIntersectedBy(l.q);
        }

        /**
         * @param pt The point to test if it is on the plane.
         * @return {@code true} If {@code pt} is on the plane.
         */
        public boolean isIntersectedBy(Point pt) {
            BigRational d = n.dx.multiply(p.x.subtract(pt.x))
                    .add(n.dy.multiply(p.y.subtract(pt.y)))
                    .add(n.dz.multiply(p.z.subtract(pt.z)));
            return d.compareTo(BigRational.ZERO) == 0;
        }

        /**
         * @param l The line to test for intersection with this.
         * @return {@code true} If this and {@code l} intersect.
         */
        public boolean isIntersectedBy(Line l) {
            if (isParallel(l)) {
                if (!isOnPlane(l)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * https://en.wikipedia.org/wiki/Line%E2%80%93plane_intersection
         *
         * @param l The line to intersect with the plane.
         * @return The intersection of the line and the plane. This is either
         * {@code null} a line or a point.
         */
        public Geometry getIntersection(Line l) {
            if (isParallel(l)) {
                if (isOnPlane(l)) {
                    return l;
                } else {
                    return null;
                }
            }
            // Are either of the points of l on the plane.
            if (isIntersectedBy(l.p)) {
                return l.p;
            }
            if (isIntersectedBy(l.q)) {
                return l.q;
            }
            BigRational num = new V3D_Vector(p, l.p).getDotProduct(n);
            BigRational den = l.v.getDotProduct(n);
            BigRational t = num.divide(den);
            return new Point(l.p.x.subtract(l.v.dx.multiply(t)),
                    l.p.y.subtract(l.v.dy.multiply(t)),
                    l.p.z.subtract(l.v.dz.multiply(t)));
        }

        /**
         * Get the distance between this and {@code pl}.
         *
         * @param p A point.
         * @param oom The order of magnitude of the precision.
         * @return The distance from {@code this} to {@code p}.
         */
        public BigDecimal getDistance(Point p, int oom) {
            if (this.isIntersectedBy(p)) {
                return BigDecimal.ZERO;
            }
            V3D_Vector v = new V3D_Vector(p, this.p);
            V3D_Vector u = this.n.getUnitVector(oom);
//        MathContext mc = new MathContext(Math_BigRationalSqrt
//                .getOOM(BigRational.ONE, oom));
            MathContext mc = new MathContext(6 - oom);
            return v.getDotProduct(u).abs().toBigDecimal(mc);
        }

    }

    /**
     * This rectangle is aligned with the axes.
     *
     * Like with {@link V3D_Rectangle, the corner points
     * {@link #p}, {@link #q}, {@link #r}, {@link #s} are rectangular and {@link #pq} is assumed to be orthogonal to {@link #qr}. The left of a
     * rectangle {@link #l} is the line segment from {@link #p} to {@link #q}. The
     * top of a rectangle {@link #t} is the line segment from {@link #q} to
     * {@link #r}. The right of a rectangle {@link #ri} is the line segment from
     * {@link #r} to {@link #s}. The bottom of a rectangle {@link #b} is the line
     * segment from {@link #s} to {@link #p}. The following depicts a generic
     * rectangle {@code
     *          t
     * q *-------------* r
     *   |             |
     * l |             | ri
     *   |             |
     * p *-------------* s
     *          b
     * }
     */
    public class Rectangle extends Plane {

        private static final long serialVersionUID = 1L;

        /**
         * The other corner of the rectangle. The others are
         * {@link #p}, {@link #q}, and {@link #r}.
         */
        protected final Point s;

        /**
         * For storing the vector from {@link #p} to {@link #q}.
         */
        protected final LineSegment l;

        /**
         * For storing the line segment from {@link #q} to {@link #r}.
         */
        protected final LineSegment t;

        /**
         * For storing the line segment from {@link #r} to {@link #s}.
         */
        protected final LineSegment ri;

        /**
         * For storing the line segment from {@link #s} to {@link #p}.
         */
        protected final LineSegment b;

        /**
         * @param p The bottom left corner of the rectangle.
         * @param q The top left corner of the rectangle.
         * @param r The top right corner of the rectangle.
         * @param s The bottom right corner of the rectangle.
         * @throws java.lang.RuntimeException iff the points do not define a
         * rectangle.
         */
        public Rectangle(Point p, Point q, Point r, Point s) {
            super(p, q, r);
            this.s = s;
            //en = new V3D_Envelope(p, q, r, s); Not initialised here as it causes a StackOverflowError
            l = new LineSegment(p, q);
            t = new LineSegment(q, r);
            ri = new LineSegment(r, s);
            b = new LineSegment(s, p);
        }

        /**
         * @param l The line to intersect with.
         * @return A point or line segment.
         */
        @Override
        public Geometry getIntersection(Line l) {
            Geometry g = new Plane(this).getIntersection(l);
            if (g == null) {
                return null;
            } else {
                if (g instanceof Point) {
                    if (this.isIntersectedBy((Point) g)) {
                        return g;
                    } else {
                        return null;
                    }
                } else {
                    Line li = (Line) g;
                    /**
                     * Get the intersection of the line and each edge of the
                     * rectangle.
                     */
                    Geometry ti = t.getIntersection(li);
                    if (ti == null) {
                        // Check ri, b, l
                        Geometry rii = ri.getIntersection(li);
                        if (rii == null) {
                            // Check b, l
                            Geometry bi = b.getIntersection(li);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li);
                                if (tli == null) {
                                    return null;
                                } else {
                                    return tli;
                                }
                            } else if (bi instanceof LineSegment) {
                                return bi;
                            } else {
                                // Check l
                                Geometry tli = this.l.getIntersection(li);
                                if (tli == null) {
                                    return bi;
                                } else {
                                    return new LineSegment((Point) bi,
                                            (Point) tli);
                                }
                            }
                        } else if (rii instanceof LineSegment) {
                            return rii;
                        } else {
                            // Check b, l
                            Geometry bi = b.getIntersection(li);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li);
                                if (tli == null) {
                                    return rii;
                                } else {
                                    return new LineSegment((Point) rii,
                                            (Point) tli);
                                }
                            } else if (bi instanceof LineSegment) {
                                return bi;
                            } else {
                                // Check l
                                Geometry tli = this.l.getIntersection(li);
                                if (tli == null) {
                                    Point riip = (Point) rii;
                                    Point bip = (Point) bi;
                                    if (riip.equals(bip)) {
                                        return bip;
                                    } else {
                                        return new LineSegment(riip, bip);
                                    }
                                } else {
                                    return new LineSegment((Point) bi,
                                            (Point) tli);
                                }
                            }
                        }
                    } else if (ti instanceof LineSegment) {
                        return ti;
                    } else {
                        // Check ri, b, l
                        Geometry rii = ri.getIntersection(li);
                        if (rii == null) {
                            // Check b, l
                            Geometry bi = b.getIntersection(li);
                            if (bi == null) {
                                // Check l
                                Geometry tli = this.l.getIntersection(li);
                                if (tli == null) {
                                    return ti;
                                } else {
                                    Point tlip = (Point) tli;
                                    Point tip = (Point) ti;
                                    if (tlip.equals(tip)) {
                                        return tlip;
                                    } else {
                                        return new LineSegment(tlip, tip);
                                    }
                                }
                            } else if (bi instanceof LineSegment) {
                                return bi;
                            } else {
                                return new LineSegment((Point) ti, (Point) bi);
                            }
                        } else {
                            Point tip = (Point) ti;
                            Point riip = (Point) rii;
                            if (tip.equals(riip)) {
                                // Check b, l
                                Geometry sri = b.getIntersection(li);
                                if (sri == null) {
                                    // Check l
                                    Geometry tli = this.l.getIntersection(li);
                                    if (tli == null) {
                                        return rii;
                                    } else {
                                        return new LineSegment(riip,
                                                (Point) tli);
                                    }
                                } else if (sri instanceof LineSegment) {
                                    return sri;
                                } else {
                                    return new LineSegment(riip, (Point) sri);
                                }
                            } else {
                                return new LineSegment(riip, tip);
                            }
                        }
                    }
                }
            }
        }
    }
}
