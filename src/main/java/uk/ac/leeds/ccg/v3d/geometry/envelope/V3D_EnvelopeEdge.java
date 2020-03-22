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

package uk.ac.leeds.ccg.v3d.geometry.envelope;

import uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;

/**
 * A class for a line segment aligned with axes. 
 * 
 * The rectangles may be infinitesimally small in terms of one (line segment) or 
 * two dimensions (point).
 * 
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_EnvelopeEdge extends V3D_LineSegment {

    /**
     * @param p One of the four corners of a face of the envelope.
     * @param q One of the four corners of a face of the envelope.
     */
    public V3D_EnvelopeEdge(V3D_Point p, V3D_Point q) {
        super(p, q);
    }
}
