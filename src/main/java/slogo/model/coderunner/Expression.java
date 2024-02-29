package slogo.model.coderunner;

import java.util.List;

/**
 * Represents all interpretable objects.
 *
 * @author Jason Qiu (GENERATED BY ExpressionClassGenerator.java)
 */

abstract class Expression {

  interface Visitor {

    double visitNumber(Number expression);

    double visitBinary(Binary expression);

    double visitLogical(Logical expression);

    double visitUnary(Unary expression);

    double visitVariable(Variable expression);

    double visitSetVariable(SetVariable expression);

    double visitDefineCommand(DefineCommand expression);

    double visitCall(Call expression);
  }

  abstract double accept(Visitor visitor);


  static class Number extends Expression {

    Number(double value) {
      this.value = value;
    }

    @Override
    double accept(Visitor visitor) {
      return visitor.visitNumber(this);
    }

    double getValue() {
      return value;
    }

    private final double value;
  }

  static class Binary extends Expression {

    Binary(Token operator, Expression left, Expression right) {
      this.operator = operator;
      this.left = left;
      this.right = right;
    }

    @Override
    double accept(Visitor visitor) {
      return visitor.visitBinary(this);
    }

    Token getOperator() {
      return operator;
    }

    Expression getLeft() {
      return left;
    }

    Expression getRight() {
      return right;
    }

    private final Token operator;
    private final Expression left;
    private final Expression right;
  }

  static class Logical extends Expression {

    Logical(Token operator, Expression left, Expression right) {
      this.operator = operator;
      this.left = left;
      this.right = right;
    }

    @Override
    double accept(Visitor visitor) {
      return visitor.visitLogical(this);
    }

    Token getOperator() {
      return operator;
    }

    Expression getLeft() {
      return left;
    }

    Expression getRight() {
      return right;
    }

    private final Token operator;
    private final Expression left;
    private final Expression right;
  }

  static class Unary extends Expression {

    Unary(Token operator, Expression right) {
      this.operator = operator;
      this.right = right;
    }

    @Override
    double accept(Visitor visitor) {
      return visitor.visitUnary(this);
    }

    Token getOperator() {
      return operator;
    }

    Expression getRight() {
      return right;
    }

    private final Token operator;
    private final Expression right;
  }

  static class Variable extends Expression {

    Variable(Token name) {
      this.name = name;
    }

    @Override
    double accept(Visitor visitor) {
      return visitor.visitVariable(this);
    }

    Token getName() {
      return name;
    }

    private final Token name;
  }

  static class SetVariable extends Expression {

    SetVariable(Token name, Expression value) {
      this.name = name;
      this.value = value;
    }

    @Override
    double accept(Visitor visitor) {
      return visitor.visitSetVariable(this);
    }

    Token getName() {
      return name;
    }

    Expression getValue() {
      return value;
    }

    private final Token name;
    private final Expression value;
  }

  static class DefineCommand extends Expression {

    DefineCommand(Token name, List<Token> parameters, List<Expression> body) {
      this.name = name;
      this.parameters = parameters;
      this.body = body;
    }

    @Override
    double accept(Visitor visitor) {
      return visitor.visitDefineCommand(this);
    }

    Token getName() {
      return name;
    }

    List<Token> getParameters() {
      return parameters;
    }

    List<Expression> getBody() {
      return body;
    }

    private final Token name;
    private final List<Token> parameters;
    private final List<Expression> body;
  }

  static class Call extends Expression {

    Call(Token command, List<Expression> arguments) {
      this.command = command;
      this.arguments = arguments;
    }

    @Override
    double accept(Visitor visitor) {
      return visitor.visitCall(this);
    }

    Token getCommand() {
      return command;
    }

    List<Expression> getArguments() {
      return arguments;
    }

    private final Token command;
    private final List<Expression> arguments;
  }

}
