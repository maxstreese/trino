/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.json.ir;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.trino.spi.function.OperatorType;
import io.trino.spi.type.Type;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class IrArithmeticBinary
        extends IrPathNode
{
    private final Operator operator;
    private final IrPathNode left;
    private final IrPathNode right;

    @JsonCreator
    public IrArithmeticBinary(
            @JsonProperty("operator") Operator operator,
            @JsonProperty("left") IrPathNode left,
            @JsonProperty("right") IrPathNode right,
            @JsonProperty("type") Optional<Type> resultType)
    {
        super(resultType);
        this.operator = requireNonNull(operator, "operator is null");
        this.left = requireNonNull(left, "left is null");
        this.right = requireNonNull(right, "right is null");
    }

    @Override
    protected <R, C> R accept(IrJsonPathVisitor<R, C> visitor, C context)
    {
        return visitor.visitIrArithmeticBinary(this, context);
    }

    @JsonProperty
    public Operator getOperator()
    {
        return operator;
    }

    @JsonProperty
    public IrPathNode getLeft()
    {
        return left;
    }

    @JsonProperty
    public IrPathNode getRight()
    {
        return right;
    }

    public enum Operator
    {
        ADD(OperatorType.ADD),
        SUBTRACT(OperatorType.SUBTRACT),
        MULTIPLY(OperatorType.MULTIPLY),
        DIVIDE(OperatorType.DIVIDE),
        MODULUS(OperatorType.MODULUS);

        private final OperatorType type;

        Operator(OperatorType type)
        {
            this.type = requireNonNull(type, "type is null");
        }

        public OperatorType getType()
        {
            return type;
        }
    }
}
