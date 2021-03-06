/*******************************************************************************
 *
 * Compilation:  javac Percolation.java
 * Execution:    java Percolation
 * Dependencies: algs4.jar
 * 
 * This program initialises a Percolation data type
 * - Creates an n-by-n grid of sites (intially all blocked)
 * 
 * @author David Price
 * @param num
 * 
 * 
 *******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int inputSize, top, bottom, gridSize;
    private WeightedQuickUnionUF quickUnion;
    private boolean[] openSites;
    
    /*
     * Create n-by-n grid, with all sites blocked
     * 
     */
    
    public Percolation(int num) {  
        if (num <= 0) {
            throw new IllegalArgumentException("inputSize (n) <= 0");
        }
        
        inputSize = num;
        gridSize = inputSize*inputSize + 2;
        top = inputSize*inputSize;
        bottom = inputSize*inputSize+1;
        
        quickUnion = new WeightedQuickUnionUF(gridSize);
        openSites = new boolean[inputSize*inputSize];
        
        // Set all sites to be blocked
        for (int i = 0; i < openSites.length; i++) {    
            openSites[i] = false;
        }
        
    }
    
    /*
     * open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        if (checkValidCoords(row, col)) {
            throw new IndexOutOfBoundsException("Invalid site coords - row: " + row + " col: " + col);
        }
        
        openSites[xyTo1D(row, col)] = true;
        
        // Union to adjacent sites if they are also open
        int site = xyTo1D(row, col);
        
        // union to virtual top if in top row
        if (row == 1) {
            quickUnion.union(site, top);
        }
        
        // Union to virtual bottom if in bottom row
        if (row == inputSize) {
            quickUnion.union(site, bottom);
        }
        
        if (row > 1) {    // site not on top
            if (isOpen(row - 1, col)) {    // If site above is open union site with it
                quickUnion.union(site, xyTo1D(row - 1, col));
            }
        }
        
        if (row < inputSize) {    // site not on bottom
            if (isOpen(row + 1, col)) {    // If site below is open union site with it
                quickUnion.union(site, xyTo1D(row + 1, col));
            }
        }
        
        if (col > 1) {    // site not at far left
            if (isOpen(row, col - 1)) {    // If site left open union site with it
                quickUnion.union(site, xyTo1D(row, col - 1));
            }
        }
        
        if (col < inputSize) {    // site not at far right
            if (isOpen(row, col + 1)) {    // If site right is open union site with it
                quickUnion.union(site, xyTo1D(row, col + 1));
            }
        }
        
    }
    
    /*
     * is site (row, col) open?
     */
    
    public boolean isOpen(int row, int col) {
        if (checkValidCoords(row, col)) {
            throw new IndexOutOfBoundsException("Invalid site coords - row: " + row + " col: " + col);
        }
        return openSites[xyTo1D(row, col)];
    }
    
    /*
     * is site (row, col) full?
     */
    
    public boolean isFull(int row, int col) {
        if (checkValidCoords(row, col)) {
            throw new IndexOutOfBoundsException("Invalid site coords - row: " + row + " col: " + col);
        }
        
        int site = xyTo1D(row, col);
        return quickUnion.connected(top, site);
        
    }
    
    /*
     * does the system percolate?
     * Are top and bottom virtual sites connected?
     */
    
    public boolean percolates() {
        return quickUnion.connected(top, bottom);
    }
    
    
    // Converts 2D array coords to 1D array indices
    private int xyTo1D(int x, int y) {
            return (x - 1) * (inputSize) + (y - 1);
    }
    
    // Checks if row and col coords are valid or invalid - returns true if any are invalid
    private boolean checkValidCoords(int row, int col) {
        return ((row < 1 || row > inputSize) || (col < 1 || col > inputSize));
    }    
    
}
