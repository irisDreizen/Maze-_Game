package Model;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;

public interface IModel {
    void generateMaze(int width, int height);
    void moveCharacter(KeyCode movement);
    int[][] getMaze();
    Solution getSolution();
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    void solveMaze();
   void saveMaze(File file);
   void loadMaze(File file);
    void stopServers();
    Position getEndPosition();
    boolean isFinished();
}
