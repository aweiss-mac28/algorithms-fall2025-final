import java.util.ArrayList;

import edu.macalester.graphics.GraphicsGroup;

/**
 * Interface implemented by answer and guess. (For sequence of up to four colors which have associated graphics).
 * @authors: Nora Betry, Courtney Brown, Elyse Quigley, and Avi Weiss
 */
public interface Sequence {
    public ArrayList<CodePin> getSequence();
    public String getSequenceAsString();
    public GraphicsGroup getSequenceGraphics();
}
