import components.stack.Stack;

    /**
     * Shifts entries between {@code leftStack} and {@code rightStack}, keeping
     * reverse of the former concatenated with the latter fixed, and resulting
     * in length of the former equal to {@code newLeftLength}.
     * 
     * @param <T>
     *            type of {@code Stack} entries
     * @param leftStack
     *            the left {@code Stack}
     * @param rightStack
     *            the right {@code Stack}
     * @param newLeftLength
     *            desired new length of {@code leftStack}
     * @updates {@code leftStack, rightStack}
     * @requires <pre>
     * {@code 0 <= newLeftLength  and
     * newLeftLength <= |leftStack| + |rightStack|}
     * </pre>
     * @ensures <pre>
     * {@code rev(leftStack) * rightStack = rev(#leftStack) * #rightStack  and
     * |leftStack| = newLeftLength}
     * }
     * </pre>
     */
    @SuppressWarnings("unused")
    private static <T> void setLengthOfLeftStack(Stack<T> leftStack,
            Stack<T> rightStack, int newLeftLength) {
        @SuppressWarnings("unchecked")
        T sub = (T) "";
        if (leftStack.length() < newLeftLength) {
            while (leftStack.length() != newLeftLength) {
                sub = rightStack.pop();
                leftStack.flip();
                leftStack.push(sub);
                leftStack.flip();
            }
        } else if (leftStack.length() > newLeftLength) {
            while (leftStack.length() != newLeftLength) {
                leftStack.flip();
                sub = leftStack.pop();
                rightStack.push(sub);
                leftStack.flip();
            }
        }
    }