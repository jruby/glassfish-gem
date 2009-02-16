/*
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.scripting.gem;

import com.sun.akuma.Daemon;
import com.sun.enterprise.glassfish.bootstrap.ASMain;

import java.io.IOException;
import java.util.Arrays;


/**
 * @author Vivek Pandey
 */
public class GlassFishMain {
    private static void startGlassFish(Options options) {
      System.out.println(options);
      System.setProperty("jruby.runtime", String.valueOf(options.runtimes));
      System.setProperty("jruby.runtime.min", String.valueOf(options.runtimes_min));
      System.setProperty("jruby.runtime.max", String.valueOf(options.runtimes_max));
      System.setProperty("rails.env", options.environment);
      System.setProperty("jruby.gem.port", String.valueOf(options.port));
      System.setProperty("GlassFish_Platform", "Static");
      //System.setProperty("glassfish.static.cache.dir", args[:app_dir]+"/tmp")
      ASMain.main(new String[]{options.appDir, "--contextroot", options.contextRoot});
    }

    public static void start(Options options){
        System.out.println("Arguments: "+options);

        Daemon d = new Daemon();
        if (d.isDaemonized()) {
            System.out.println("Starting GlassFish server on port: " + System.getProperty(""));
            try {
                d.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // if you are already daemonized, no point in daemonizing yourself again,
            // so do this only when you aren't daemonizing.
            if (options.daemon) {
                try {

                    //TODO: patch JVM args to suit GlassFish
                    d.daemonize();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }

        startGlassFish(options);
    }

    public static void main(String[] args) {
        System.out.println(" Arguments: "+ Arrays.toString(args));

        Daemon d = new Daemon();
        if (d.isDaemonized()) {
            System.out.println("Starting GlassFish server on port: " + System.getProperty(""));
            try {
                d.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // if you are already daemonized, no point in daemonizing yourself again,
            // so do this only when you aren't daemonizing.
            if (true) {
                try {
                    d.daemonize();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }
        // your normal main code follows
        // this part can be executed in two ways
        // 1) the user runs your process in the foreground
        // 2) you decided to daemonize yourself, in which case the newly forked daemon will execute this code,
        //    while the originally executed foreground Java process exits before it gets here.
    }



}
