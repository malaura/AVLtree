public class AVLTree {
	Node root;
	public AVLTree(){
		root = null;
	}

	/**
	 * Non recursive public insert method. 
	 * @param v Value to be inserted. 
	 */
	public void insert(String v) {
		int val = Integer.parseInt(v); 
		Node n = new Node(val);			
		root = insertNode(root, n);		
	}

	/**
	 * Method to compare the value to be inserted with a node and either go to the left or right, until we find a null or a duplicate.
	 * @param current Represents the root of the subtree that the node is being inserted into. 
	 * @param n The node being inserted. 
	 * @return A reference to the node.
	 */
	private Node insertNode(Node current, Node n) {		
		if(current == null){ 			
			current = n;				 
			System.out.println("Insert done.");
		}
		else if(current.data < n.data){	
			current.right = insertNode(current.right, n);	
		}
		else if(current.data > n.data){
			current.left = insertNode(current.left, n);		
		}
		else if(current.data == n.data){	
			System.out.println("Duplicate entry");
		}

		current.height = updateHeight(current);
		return current = checkBalance(current);	
	}

	
	

	/**
	 * Public method that updates the height of the node. 
	 * @param node The current node.
	 * @return int which will be the updated height
	 */	
	public int updateHeight(Node current) {
		return Math.max(getHeight(current.left) , getHeight(current.right)) + 1;
	}
	
	/**
	 * Public method that checks if the node is balanced.
	 * @param node The current node.
	 * @return current node after rotation (if it was done) or just the current node without any changes.
	 */	
	public Node checkBalance(Node current) {
		int balanceFactor = balanceFactor(current);
		if(balanceFactor == -2){
			if(getHeight(current.right.left) - getHeight(current.right.right) <= 0){
				current = Left(current);
			}
			else{
				current = RightLeft(current);
		}
		}
		else if(balanceFactor == 2){
			if(getHeight(current.left.left) - getHeight(current.left.right) >= 0){
				current = Right(current);			
				}
			else{
				current = LeftRight(current);
			}
		}
		return current;
	}

	/**
	 * Public method to that rotates right and left.
	 * @param node The current node.
	 * @return Left(node) node after rotation
	 */	
	public Node RightLeft(Node node){
		node.right = Right(node.right);
		return Left(node);
	}
	
	/**
	 * Public method to that rotates left and right.
	 * @param node The current node.
	 * @return Right(node) node after rotation
	 */	
	public Node LeftRight(Node node){
		node.left = Left(node.left);
		return Right(node);
	}
	

	/**
	 * Public method to that rotates to the left.
	 * @param node The current node.
	 * @return temp node after rotation
	 */	
	public Node Left(Node node){
		Node temp = node.right;
		node.right = temp.left;
		temp.left = node;
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		temp.height = Math.max(getHeight(temp.left), getHeight(temp.right)) + 1;

		return temp;
	}




	/**
	 * Public method to that rotates to the right.
	 * @param node The current node.
	 * @return temp node after rotation
	 */
	public Node Right(Node node){
		Node temp = node.left;
		node.left = temp.right;
		temp.right = node;
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
		temp.height = Math.max(getHeight(temp.left), getHeight(temp.right)) + 1;
		return temp;
	}

	/**
	 * Public method to check for the height. If the node is null, then the height is 0. Else, returns the height of the node. 
	 * @param node The current node.
	 * @return int height
	 */
	public int getHeight(Node node){
		if(node == null){
			return 0;
		}

		return node.height;

	}

	/**
	 * Public method to check for the balance factor. The height of the left node minus the height of the right node. 
	 * @param node The current node.
	 * @return int the balance factor. 
	 */
	public int balanceFactor(Node node){
		return getHeight(node.left) - getHeight(node.right);
	}

	/**
	 * Public method to check if a value exists in the tree. This is just a wrapper. A recursive search is called within.  
	 * @param v The value.
	 */
	public void search(String v) {
		int val = Integer.parseInt(v);
		if(searchNode(root, val)){
			System.out.println("Node was found!");
		}
		else{
			System.out.println("Node not found");
		}
	}

	/**
	 * Private recursive method. To search for the string.
	 * @param current The current represents the root of a given subtree. 
	 * @param val The value being searched for.
	 * @return boolean true or false, depending on whether the node has been found.
	 */
	private boolean searchNode(Node current, int val) {
		if(current != null){
			if(current.data == val){
				return true;
			}
			else if(current.data > val){
				return searchNode(current.left, val);	
			}
			else if(current.data < val){
				return searchNode(current.right, val);
			}
		}
		return false;
	}


	
	/**
	 * Public traverse method. 
	 *
	 */
	public void traverse() {
		traversePreOrder(root);
		System.out.println();

	}

	
	/**
	 * private traverse method. 
	 * @param current Node to be traversed
	 */
	private void traversePreOrder(Node current) {
		if(current!=null){
			System.out.print(current.data + " ");
			traversePreOrder(current.left);
			traversePreOrder(current.right);
		}	

	}

	/**
	 * Public delete method. 
	 * @param v value to be deleted.
	 */
	public void delete(String v) {
		int val = Integer.parseInt(v);
		if(searchNode(root, val)){	
			System.out.println("Node deleted.");
		}
		root = findDeleteNode(root, val);	
	}

	/**
	 * Method to simply arrive at the node which needs to be deleted if it exists in the tree.
	 * @param current The current node, which represents the root of a subtree.
	 * @param val The value to be deleted.
	 * @return A reference to an updated Node. 
	 */
	private Node findDeleteNode(Node current, int val) {
		if(current == null){	
			System.out.println("Node not found.");
			return current;
		}
		else if(current.data == val){	
			return deleteCurrentNode(current);
		}
		else if(current.data > val){	
			current.left = findDeleteNode(current.left, val);
		}
		else if(current.data < val){
			current.right = findDeleteNode(current.right, val);
		}
		current.height = updateHeight(current);
		return current = checkBalance(current);	
		
	}

	/**
	 * We now are at the node that needs to be deleted. Identify the case and handle accordingly.
	 * @param current The node that needs to be deleted. 
	 * @return A reference to whatever the parent of current should point to. 
	 */
	private Node deleteCurrentNode(Node current) {
		int icase = IdentifyCase(current);
		if(icase == 1){ //no children
			return null;
		}
		else if(icase == 2){ // one child
			return deleteCase2(current);
		}
		else if(icase == 3){ //two children
			return deleteCase3(current);
		}
		return null;
	}

	/**
	 * Method to delete a node with only one child. Whatever is returned from this method, will replace the current node in the tree.
	 * @param current The node which needs to be deleted.
	 * @return The replacement. 
	 */
	private Node deleteCase2(Node current) {
		if(current.left == null){
			return current.right;
		}
		return current.left;
	}

	/**
	 * Method to delete a node with 2 children. 
	 * @param current The node to be deleted. 
	 * @return The node that the parent will point to. 
	 */
	private Node deleteCase3(Node current) {
		int replaceInt = findLowestInRightSubTree(current.right);	
		current.setData(replaceInt);								
		current.right = findDeleteNode(current.right, replaceInt);	
		return current;
	}

	/**
	 * Method to find the lowest value in the right subtree.
	 * @param current The root of the right subtree.
	 * @return Value of the lowest node in the subtree.
	 */
	private int findLowestInRightSubTree(Node current) {
		int val = current.data;
		while(current.left != null){
			current = current.left;
			val = current.data;
		}
		return val;
	}

	/**
	 * Method to identify how many children a node has.
	 * @param current The node that needs to be checked.
	 * @return A case number representing the above. 
	 */
	private int IdentifyCase(Node current) {
		if(current.left == null && current.right == null){
			return 1;
		}
		else if(current.left != null && current.right != null){
			return 3;
		}
		return 2;
	}
}