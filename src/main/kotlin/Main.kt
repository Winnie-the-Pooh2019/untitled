import java.util.*


data class Contact(
    var name: String,
    var phoneNumber: String
)

class MobilePhone(val myNumber: String, private val myContacts: Collection<Contact>) {

    fun addNewContact(contact: Contact) = myContacts.toMutableSet().add(contact)

    fun updateContact(old: Contact, new: Contact): Boolean {
        val contact = myContacts.firstOrNull { it.name == old.name } ?: return false

        contact.name = new.name
        contact.phoneNumber = new.phoneNumber

        return true
    }

    fun removeContact(contact: Contact) = myContacts.toMutableList().remove(contact)

    fun queryContact(name: String) = myContacts.firstOrNull { it.name == name }

    fun printContacts() = myContacts.forEach(::println)
}


class BinaryTree {
    var root: Node? = null
    
    fun add(value: Int) {
        root = addRecursive(root, value)
    }

    private fun addRecursive(current: Node?, value: Int): Node {
        if (current == null) {
            return Node(value)
        }
        if (value < current.value) {
            current.left = addRecursive(current.left, value)
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value)
        }
        return current
    }

    val isEmpty: Boolean
        get() = root == null
    val size: Int
        get() = getSizeRecursive(root)

    private fun getSizeRecursive(current: Node?): Int {
        return if (current == null) 0 else getSizeRecursive(current.left) + 1 + getSizeRecursive(current.right)
    }

    fun containsNode(value: Int): Boolean {
        return containsNodeRecursive(root, value)
    }

    private fun containsNodeRecursive(current: Node?, value: Int): Boolean {
        if (current == null) {
            return false
        }
        if (value == current.value) {
            return true
        }
        return if (value < current.value) containsNodeRecursive(
            current.left,
            value
        ) else containsNodeRecursive(current.right, value)
    }

    fun delete(value: Int) {
        root = deleteRecursive(root, value)
    }

    private fun deleteRecursive(current: Node?, value: Int): Node? {
        if (current == null) {
            return null
        }
        if (value == current.value) {
            if (current.left == null && current.right == null) {
                return null
            }

            if (current.right == null) {
                return current.left
            }
            if (current.left == null) {
                return current.right
            }

            val smallestValue = findSmallestValue(current.right)
            current.value = smallestValue
            current.right = deleteRecursive(current.right, smallestValue)
            return current
        }
        if (value < current.value) {
            current.left = deleteRecursive(current.left, value)
            return current
        }
        current.right = deleteRecursive(current.right, value)
        return current
    }

    private fun findSmallestValue(root: Node?): Int {
        return if (root!!.left == null) root.value else findSmallestValue(root.left)
    }

    fun traverseInOrder(node: Node?) {
        if (node != null) {
            traverseInOrder(node.left)
            visit(node.value)
            traverseInOrder(node.right)
        }
    }

    fun traversePreOrder(node: Node?) {
        if (node != null) {
            visit(node.value)
            traversePreOrder(node.left)
            traversePreOrder(node.right)
        }
    }

    fun traversePostOrder(node: Node?) {
        if (node != null) {
            traversePostOrder(node.left)
            traversePostOrder(node.right)
            visit(node.value)
        }
    }

    fun traverseLevelOrder() {
        if (root == null) {
            return
        }
        val nodes: Queue<Node?> = LinkedList()
        nodes.add(root)
        while (!nodes.isEmpty()) {
            val node = nodes.remove()
            print(" " + node!!.value)
            if (node.left != null) {
                nodes.add(node.left)
            }
            if (node.right != null) {
                nodes.add(node.right)
            }
        }
    }

    fun traverseInOrderWithoutRecursion() {
        val stack = Stack<Node>()
        var current = root
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current)
                current = current.left
            }
            val top = stack.pop()
            visit(top.value)
            current = top.right
        }
    }

    fun traversePreOrderWithoutRecursion() {
        val stack = Stack<Node>()
        var current = root
        stack.push(root)
        while (current != null && !stack.isEmpty()) {
            current = stack.pop()
            visit(current.value)
            if (current.right != null) stack.push(current.right)
            if (current.left != null) stack.push(current.left)
        }
    }

    fun traversePostOrderWithoutRecursion() {
        val stack = Stack<Node>()
        var prev = root
        var current = root
        stack.push(root)
        while (current != null && !stack.isEmpty()) {
            current = stack.peek()
            val hasChild = current.left != null || current.right != null
            val isPrevLastChild = prev === current.right || prev === current.left && current.right == null
            if (!hasChild || isPrevLastChild) {
                current = stack.pop()
                visit(current.value)
                prev = current
            } else {
                if (current.right != null) {
                    stack.push(current.right)
                }
                if (current.left != null) {
                    stack.push(current.left)
                }
            }
        }
    }

    private fun visit(value: Int) {
        print(" $value")
    }

    inner class Node(var value: Int) {
        var left: Node? = null
        var right: Node? = null
    }
}

fun main(args: Array<String>) {
    val tree = BinaryTree()

    tree.add(6)
    tree.add(4)
    tree.add(8)
    tree.add(3)
    tree.add(5)
    tree.add(7)
    tree.add(9)
}