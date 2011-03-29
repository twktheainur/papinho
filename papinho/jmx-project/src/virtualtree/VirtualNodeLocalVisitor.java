package virtualtree;

public class VirtualNodeLocalVisitor implements VirtualNodeVisitor {
	public void visit(VirtualNode node) {
		if (node.getChildren().isEmpty()) {
			System.out.println("Child");
		} else {
			System.out.println("Parent");
		}
	}
}
