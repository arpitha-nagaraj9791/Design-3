/* 
Time Complexity:
  - hasNext: Amortized O(1)
  - next: O(1)

Space Complexity: O(d) for the stack of iterators, where d is the maximum nesting depth

Did this code successfully run on Leetcode: Yes
*/

// Approach:
// - Maintain a stack of iterators. Start by pushing the top-level list iterator.
// - hasNext():
//     * While the stack isn't empty:
//         - If top iterator is exhausted, pop it.
//         - Else fetch next element:
//             · If it's an integer, cache it in nextEle and return true.
//             · If it's a list, push its iterator and continue looping.
// - next(): return the cached nextEle.getInteger() set by hasNext().
// - This lazy flattening ensures each element is processed once; stack depth reflects nesting.

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class NestedListIterator implements Iterator<Integer> {
    Stack<Iterator<NestedInteger>> st;
    NestedInteger nextEle;

    public NestedListIterator(List<NestedInteger> nestedList) {
        this.st = new Stack<>();
        st.push(nestedList.iterator());
    }

    @Override
    public Integer next() {
        return nextEle.getInteger();
    }

    @Override
    public boolean hasNext() {
        while(!st.isEmpty()){
            if(!st.peek().hasNext()){
                st.pop();
            }else{
                nextEle = st.peek().next();
                if(nextEle.isInteger()){
                    return true;
                }else{
                    st.push(nextEle.getList().iterator());
                }
            }
        }
        return false;
    }
}
