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
package uk.ac.leeds.ccg.vector.visualisation;

import java.awt.Graphics2D;
import javax.swing.JApplet;
import javax.swing.JFrame;
import uk.ac.leeds.ccg.vector.core.Vector_Environment;

/**
 * Abstract render class.
 */
public abstract class Vector_AbstractRender extends JApplet {

    public Vector_Environment ve;
   
    public int Width = 512;
    public int Height = 256;
    //public int scale = 50;

    public JFrame _JFrame;
    public Graphics2D _Graphics2D;
    
    protected Vector_AbstractRender(){}

    public Vector_AbstractRender(
            Vector_Environment ve){
        this.ve = ve;
    }

}
