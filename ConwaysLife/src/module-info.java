module ConwaysLife {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires jdk.compiler;
	
	opens application to javafx.graphics, javafx.fxml;
}
