package com.string.problem.model

class DoubleLinkListNode {

    var ref: String? = null
    var next: DoubleLinkListNode? = null
    var prev: DoubleLinkListNode? = null

    constructor(ref: String?) {
        this.ref = ref
    }

    fun removeBinding() {
        if (prev != null) {
            prev?.next = next
        }
        if (next != null) {
            next?.prev = prev
        }
        prev = null
        next = null
    }
}