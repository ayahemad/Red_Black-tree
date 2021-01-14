package trees;
import java.util.Iterator;

public class RedBlackTree<E> extends AbstractBinaryTree<E> {

	protected static class Node<E> implements Position<E> {
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;
		private int color;
		

		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild, int c) {
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
			color = c;
			
		}
		
		
		public E getElement() {
			return element;
		}

		public Node<E> getParent() {
			return parent;
		}

		public Node<E> getLeft() {
			return left;
		}

		public Node<E> getRight() {
			return right;
		}
		
		public int getColor() {
			return color;
		}
		
		
		public void setElement(E e) {
			element = e;
		}

		public void setParent(Node<E> parentNode) {
			parent = parentNode;
		}

		public void setLeft(Node<E> leftChild) {
			left = leftChild;
		}

		public void setRight(Node<E> rightChild) {
			right = rightChild;
		}
		
		public void setColor(int col) {
			color = col;
		}
	}

	/** Factory function to create a new node storing element e. */
	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right, int c, boolean db) {
		return new Node<E>(e, parent, left, right,c);
	}

	protected Node<E> root = null;
	private int size = 0;

	public RedBlackTree() {
	}

	/** Validates the position and returns it as a node. */
	protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
		if (!(p instanceof Node))
			throw new IllegalArgumentException("Not valid position type");
		Node<E> node = (Node<E>) p;
		if (node.getParent() == node)
			// our convention for defunct node
			throw new IllegalArgumentException("p is no longer in the tree");
		return node;
	}

	// accessor methods (not already implemented in AbstractBinaryTree)
	/** Returns the number of nodes in the tree. */
	public int size() {
		return size;
	}
	
	

	/** Returns the root Position of the tree (or null if tree is empty). */
	public Position<E> root() {
		return root;
	}

	/** Returns the Position of p's parent (or null if p is root). */
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent();
	}

	/** Returns the Position of p's left child (or null if no child exists). */
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getLeft();
	}

	/** Returns the Position of p's right child (or null if no child exists). */
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getRight();
	}

	// update methods supported by this class
	/**
	 * Places element e at the root of an empty tree and returns its new Position.
	 */
	public Position<E> addRoot(E e) throws IllegalStateException {
		if (!isEmpty())
			throw new IllegalStateException("Tree is not empty");
		root = createNode(e, null, null, null, 0, false);
		size = 1;
		return root;
	}

	/**
	 * Creates a new left child of Position p storing element e; returns its
	 * Position.
	 */
//	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
//		Node<E> parent = validate(p);
//		if (parent.getLeft() != null)
//			throw new IllegalArgumentException("p already has a left child");
//		Node<E> child = createNode(e, parent, null, null);
//		parent.setLeft(child);
//		size++;
//		return child;
//	}

	/**
	 * Creates a new right child of Position p storing element e; returns its
	 * Position.
	 */
//	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
//		Node<E> parent = validate(p);
//		if (parent.getRight() != null)
//			throw new IllegalArgumentException("p already has a right child");
//		Node<E> child = createNode(e, parent, null, null);
//		parent.setRight(child);
//		size++;
//		return child;
//	}

	/**
	 * Replaces the element at Position p with e and returns the replaced element.
	 */
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E temp = node.getElement();
		node.setElement(e);
		return temp;
	}

//	/** Removes the node at Position p and replaces it with its child, if any. */
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if (numChildren(p) == 2)
			throw new IllegalArgumentException("p has two children");
		Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
		if (child != null)
			child.setParent(node.getParent()); // child’s grandparent becomes its parent
		if (node == root)
			root = child;
		else {
			Node<E> parent = node.getParent();
			if (node == parent.getLeft())
				parent.setLeft(child);
			else
				parent.setRight(child);
		}
		size--;
		E temp = node.getElement();
		node.setElement(null);
		node.setLeft(null);
		node.setRight(null);
		node.setParent(node);
		return temp;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Position<E>> positions() {
		// TODO Auto-generated method stub
		return null;
	}

    public void preorderTraverse( Position <E> p) {
    	Node<E> node = validate(p);
    	System.out.println(node.getElement());
    	if(numChildren(p) == 0)
    		return ;
    	Iterable<Position<E>> child = children(p);
    	for(Position<E> c: child) {
    		preorderTraverse(c);
    	}
    }
    
    public void postorderTraverse(Position<E> p) {
    	Node<E> node = validate(p);
    	if(numChildren(p) == 0) {
    		System.out.println(node.getElement());
    		return ;
    		}
    	Iterable<Position<E>> child = children(p);
    	for(Position<E> c: child) {
    		postorderTraverse(c);
    	}
    	System.out.println(node.getElement());
    	
    }
    
    
    public Node<E> getRedChild(Node<E> node){   // returns the red child if exist, or null
		if(node.getLeft() != null || node.getRight() != null ) {
			if(node.getLeft().getColor() == 1)
				return node.getLeft();
			else if (node.getRight().getColor() == 1)
				return node.getRight();
		}
		
		return null;
	}

    
    Position<E> inorderPredecessor (Position<E> p){
    	Node<E> node = validate(p);
    	if (node.getRight() != null)
    		return inorderPredecessor(node.getRight());
    	else
    		return p;
    }
    
    
    public void delete(Position<E> p) {
    	Node<E> node = validate(p);
    	if(numChildren(p) <= 1) {
    		boolean d = node.getColor() == 0;
    		remove(p);
    		rebalanceDelete(p, d);
    	}
    	else {
    		Position<E>i = inorderPredecessor(p);
    		// swapping entries
    		E ep = set(p, i.getElement());
    		E ee = set(i, ep);
    		delete(i);
    	}
 
    }
    
    
    public void rebalanceDelete(Position<E> p, boolean d) {
    	Node<E> node = validate(p);
    	if(node.getColor() == 1) {
    		node.setColor(0);
    	}
    	else if(node.getParent() == null && d) {
    		resolveDoubleBlack(p);
    	}
    }
    
    public void left_rotation(Node<E> node) {
    	Node<E> y = node.getRight();
    	node.setRight(y.getLeft());
    	if(y.getLeft() != null)
    		y.getLeft().setParent(node);
    	y.setParent(node.getParent());
    	if(node.getParent() == null)
    		root = y;
    	else if (node == node.getParent().getLeft())
    		node.getParent().setLeft(y);
    	else
    		node.getParent().setRight(y);
    	y.setLeft(node);
    	node.setParent(y);
    		
    }
    
    
    public void resolveDoubleBlack(Position<E> p) {
    	Node<E> y = (Node<E>) sibling(p);
    	Node<E> z = (Node<E>) parent(p);
    	if(y.getColor() == 0) {            
    		if(getRedChild(y) != null) {
    			Node<E> x = getRedChild(y);

    			rotate(y, z);                   // rotate z right rotation if y was on left of it, or vice versa
    			
    			int z_color = z.getColor();
    			x.setColor(0); z.setColor(0);
    			y.setColor(z_color);
    		}
    		else {
    			y.setColor(1);
    			if(z.getColor() == 1) 
    				z.setColor(0);
    			else if(!isRoot(z))
    				resolveDoubleBlack(z);
    		}
    	}
    	
    	else {
    		rotate(y, z);     // rotate z right rotation if y was on left of it, or vice versa
    		y.setColor(0);
    		z.setColor(1);
    		resolveDoubleBlack(p);
    	}
    }

}