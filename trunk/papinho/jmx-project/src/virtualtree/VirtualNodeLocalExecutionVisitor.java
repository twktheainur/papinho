package virtualtree;

public class VirtualNodeLocalExecutionVisitor implements VirtualNodeVisitor {
	public void visit(VirtualNode node) {
		if (node.getChildren().isEmpty()) {
			System.out.println("Child");
		} else {
			System.out.println("Parent");
		}
	}
}
