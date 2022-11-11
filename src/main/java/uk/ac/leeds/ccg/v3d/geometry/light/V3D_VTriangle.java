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
package uk.ac.leeds.ccg.v3d.geometry.light;

import java.io.Serializable;

/**
 * For representing and processing lightweight triangles in 3D. The triangle is
 * made of 3 V3D_VLine instances {@link #pq}, {@link #qr} and {@link #rp}. The 
 * following depicts a triangle {@code
 *
 *  p *- - - - - - - - - - - - - - - - - - - - - - -* q
 *     \                                           /
 *      \                                         /
 *       \                                       /
 *        \                                     /
 *         \                                   /
 *          \                                 /
 *           \                               /
 *            \                             /
 *             \                           /
 *              \                         /
 *               \                       /
 *                \                     /
 *                 \                   /
 *                  \                 /
 *                   \               /
 *                    \             /
 *                     \           /
 *                      \         /
 *                       \       /
 *                        \     /
 *                         \   /
 *                          \ /
 *                           *
 *                           r
 * }
 * For more complicated triangles uk.ac.leeds.ccg.v3d.geometry.V3D_Triangle.
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_VTriangle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The pq edge.
     */
    public V3D_VLine pq;

    /**
     * The qr edge.
     */
    public V3D_VLine qr;

    /**
     * The rp edge.
     */
    public V3D_VLine rp;

    /**
     * Creates a new triangle.
     *
     * @param pq What {@link #pq} is set to.
     * @param qr What {@link #qr} is set to.
     * @param rp What {@link #rp} is set to.
     */
    public V3D_VTriangle(V3D_VLine pq, V3D_VLine qr, V3D_VLine rp) {
        this.pq = pq;
        this.qr = qr;
        this.rp = rp;
    }

    @Override
    public String toString() {
        return toString("");
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
     * @return A description of the fields.
     */
    protected String toStringFields(String pad) {
        return pad + "pq=" + pq.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "qr=" + qr.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "rp=" + rp.toString(pad);
    }

//    @Override
//    public V3D_V getCentroid() {
//        return new V3D_V(
//                (p.x.add(q.x).add(r.x)).divide(3).add(offset.x),
//                (p.y.add(q.y).add(r.y)).divide(3).add(offset.y),
//                (p.z.add(q.z).add(r.z)).divide(3).add(offset.z));
//    }

}
