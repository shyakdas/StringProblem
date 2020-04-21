package com.string.problem.model

class LinkedListHelper {

    var head: DoubleLinkListNode? = null
    var tail: DoubleLinkListNode? = null

    fun removeTail(): String? {
        if (tail == null) {
            return null
        }
        val temp = tail?.ref
        if (tail == head) {
            head = null
            tail = null
        }

        tail = tail?.prev
        tail?.next = null
        return temp
    }

    fun removeHead(): String? {
        if (head == null) {
            return null
        }
        val temp = head?.ref
        if (tail == head) {
            head = null
            tail = null
        }

        head = head?.next
        head?.prev = null
        return temp
    }

    fun removeNode(node: DoubleLinkListNode)
    {
        if (tail?.ref == node.ref)
        {
            removeTail()
        }
        else if (head?.ref == node.ref)
        {
            removeHead()
        }
        else
        {
            node.next?.prev = node.prev
            node.prev?.next = node.next
        }
    }

    fun setHeadTo(node: DoubleLinkListNode?) {
        if (head == node) return
        else if (head == null) {
            head = node
            tail = node
        } else if (head == tail) {
            tail?.prev = node
            head = node
            head?.next = tail
        } else {
            if (tail == node) {
                removeTail()
            }
            node?.removeBinding()
            head?.prev = node
            node?.next = head
            head = node
        }
    }
}