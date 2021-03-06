
public class BinaryTree 
{
	private TreeNode root;
	//Since we have 4 row, with 0 base, the max data is 2^5-1, and offset 0 to empty because it is more comment to iterate all the elements this way.
	//We can tide the size of the array with the depth, so it can be more dynamic 

	//Empty tree
	public BinaryTree ()
	{
		root =null;
	}

	public BinaryTree (TreeNode t)
	{
		root = t;
	}

	//Copy Constructor 
	public BinaryTree (BinaryTree t)
	{
		root = t.root;
	}

	public void add (int d) throws Exception
	{
		if (d>99 || d<0)
		{
			throw new Exception ("Check input value");
		}
		else 
			root = insert (d, root);
	}

	//This is to look to insert the TreeNode to the correct branch.
	private static TreeNode insert (int d, TreeNode t)
	{
		if (t==null)
		{
			return new TreeNode (d);
		}
		else if (t.getData()>d)
		{
			t.left = (insert(d,t.left));
		}
		else if (t.getData()<d)
		{
			t.right = (insert(d, t.right));
		}
		return t;
	}

	public BinaryTree getLeftTree () throws Exception
	{
		if (this.isEmpty())
		{
			throw new Exception ("The tree is empty");
		}
		else 
			return new BinaryTree (this.root.left);
	}
	public BinaryTree getRightTree () throws Exception
	{
		if (this.isEmpty())
		{
			throw new Exception ("The tree is empty");
		}
		else
			return new BinaryTree (this.root.right);
	}

	public void setLeftTree (BinaryTree l) throws Exception
	{
		if (this.root == null)
		{
			throw new Exception ("Root is Empty");
		}
		this.root.left = (l.root);
	}
	public void setRightTree (BinaryTree r) throws Exception
	{
		if (this.root == null)
		{
			throw new Exception ("Root is Empty");
		}
		this.root.right = (r.root);
	}

	public int getRootObject () throws Exception
	{
		if (this.isEmpty())
		{
			throw new Exception("Tree is empty");
		}
		return root.data;
	}
	public boolean isEmpty()
	{
		if (root==null)
			return true;

		return false;
	}
	public void makeEmpty ()
	{
		this.root = null;
	}
	public static void draw (BinaryTree t) throws Exception
	{
		//use RootDepth as a guild of offset
		int [] allData = new int [32];
		fillData(t, allData);
		int rootDepth = TreeNode.NodeDepth(t.root);
		char [][] picTree = new char [rootDepth*2+1][(int) (8*Math.pow(2, rootDepth))];
		//[even = number , odd = "/" or "\" ][if depth 0, the array will be 8 and it grows by 8* 2^n where n = depth of the tree ]
		//To clean the tree
		for ( int i = 0 ; i < picTree.length ; i ++)
		{
			for (int j = 0 ; j < picTree[i].length ; j ++)
			{
				picTree[i][j]= ' ';
			}
		}
		int start = picTree[0].length/2;
		int jump = picTree[0].length;

		//draw numbers
		picTree = drawNumber(allData, picTree, start, jump, 0, 1);
		
		//reset start and jump for bars starting at the second line, which automatically divide by half from original start and jump.
		//This is because we don't draw bar for the root.
		jump = start;
		start = start/2;
		picTree = drawBar (allData, picTree, start, jump, 1);
		
		
		/*
		 * This is the drawing for double for loop, which I think is faster and better.
		 * See recursive function in the bottom.
		for (int i = 0 ; i < picTree.length ; i=i+2)
		{

			for (int j = start ; j < picTree[i].length ; j=j+jump)
			{
				// if number is greater than ten, take two space.
				if (allData[counter] !=0)
				{
					//This will avoid print the number 0 if the digit is less than 10.
					if (allData[counter]/10 !=0)
					picTree[i][j-1]=(char) ((char)'0'+(allData[counter]/10));
					picTree[i][j]=(char) ((char) '0'+(allData[counter]%10));
				}
				counter++;
			}
			jump = start;
			start = start/2;
		}
		 */
		/*
		int leftRight =0;
		for (int i = 1; i< picTree.length ; i=i+2)
		{
			for (int j = start; j <picTree[i].length ; j=j+jump,  leftRight= (leftRight+1)%2)
			{
				if (leftRight == 0 && (picTree[i+1][j] != 0))
				{
					picTree[i][(((j+j+jump)/2)+j)/2] = '/';
					//draw "/"
				}
				if (leftRight == 1 && picTree[i+1][j] !=0)
				{
					picTree[i][(((j+j-jump)/2)+j)/2] = '\\' ;
					//draw "\"
				}
			}
			jump = start;
			start = start/2;
		}
		 */
		for ( int i = 0 ; i < picTree.length ; i ++)
		{
			for ( int j = 0 ; j < picTree[i].length ; j ++)
			{
				if (picTree[i][j] >= '0' || picTree[i][j] <='9')
				{
					System.out.print(picTree[i][j]);
				}
				else 
				{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	//This is to write the tree to an array.
	//Might be better if it is static.
	private static void fillData (BinaryTree t, int allData []) throws Exception
	{
		if (!t.isEmpty())
		{
			//Remainder we never used index 0 so we can read the data better. 
			allData[1] = t.getRootObject();
			fillData (t.getLeftTree(), 2, allData);
			fillData (t.getRightTree(), 3, allData);
		}
	}
	private static void fillData (BinaryTree t, int index, int allData[]) throws Exception
	{
		if (t.isEmpty())
		{
			return;
		}
		else
		{
			allData[index] = t.getRootObject();
			fillData (t.getLeftTree(), 2*index, allData);
			fillData (t.getRightTree(), 2*index+1, allData);
		}
	}

	private static char [][] drawNumber (int allData [], char picTree [][], int start, int jump, int row, int counter)
	{
		if (row>=picTree.length)
			return picTree;
		else
		{
			for(int j = start ; j < picTree[row].length ; j = j + jump)
			{
				if (allData[counter] !=0)
				{
					if (allData[counter]/10 !=0)
						picTree[row][j-1]=(char) ((char)'0'+(allData[counter]/10));
					picTree[row][j]=(char) ((char) '0'+(allData[counter]%10));
				}
				counter++;
			}
		}
		return drawNumber ( allData,  picTree,  start/2,  start, row+2, counter);
	}

	private static char [][] drawBar (int allData[] , char picTree [][], int start, int jump, int row)
	{
		//Need two different type of function to draw bars.
		//if 0 draw "/"
		//if 1 draw "\"
		int leftRight = 0;
		if (row >= picTree.length)
			return picTree;
		else
		{
			for (int j = start; j <picTree[row].length ; j=j+jump,  leftRight= (leftRight+1)%2)
			{
				if (leftRight == 0 && (picTree[row+1][j] >= '0' && (picTree[row+1][j] <= '9')))
				{
					picTree[row][(((j+j+jump)/2)+j)/2] = '/';
					//draw "/"
				}
				if (leftRight == 1 &&  (picTree[row+1][j] >= '0' && (picTree[row+1][j] <= '9')))
				{
					picTree[row][(((j+j-jump)/2)+j)/2] = '\\' ;
					//draw "\"
				}
			}
		}
		return drawBar(allData, picTree, start/2 , start, row+2);
	}
}	

