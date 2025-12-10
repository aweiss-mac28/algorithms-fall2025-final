import java.util.ArrayList;

import edu.macalester.graphics.GraphicsGroup;

public interface Sequence {
    public ArrayList<CodePin> getSequence();
    public String getSequenceAsString();
    public GraphicsGroup getSequenceGraphics();
}
