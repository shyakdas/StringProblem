package com.string.problem.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.string.problem.model.DoubleLinkListNode
import com.string.problem.model.LinkedListHelper
import com.string.problem.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var mutableOlderStackData = MutableLiveData<ArrayList<String>>()
    var mutableRecentStackData = MutableLiveData<ArrayList<String>>()
    var cache = HashMap<String, DoubleLinkListNode>()
    var oldCache = HashMap<String, DoubleLinkListNode>()
    private var currentSize: Int = 0
    private var currentSizeOfOld: Int = 0
    private var linkedListHelper = LinkedListHelper()
    private var olderLinkedListHelper = LinkedListHelper()

    fun addStringValueToQueue(stringValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            insertElementInCache(stringValue)
            withContext(Dispatchers.Main) {
                getRecentList()
                getOldList()
            }
        }
    }

    private fun getRecentList() {
        var doubleLinkListNode = linkedListHelper.tail
        val list = ArrayList<String>()
        while (doubleLinkListNode != null) {
            doubleLinkListNode.ref?.let {
                list.add(it)
            }
            doubleLinkListNode = doubleLinkListNode.prev
        }
        mutableRecentStackData.value = list
    }

    private fun getOldList() {
        var doubleLinkListNode = olderLinkedListHelper.tail
        val list = ArrayList<String>()
        while (doubleLinkListNode != null) {
            doubleLinkListNode.ref?.let {
                list.add(it)
            }
            doubleLinkListNode = doubleLinkListNode.prev
        }
        mutableOlderStackData.value = list
    }

    private fun insertElementInCache(string: String) {
        if (!cache.contains(string)) {
            if (oldCache.containsKey(string)) {
                removeParticularElementFromOldCache(string)
                insertElementInCache(string)
                return
            }
            if (currentSize == Constant.RECENT_STRING_MAX_SIZE) {
                removeLeastRecentElement()
            } else {
                currentSize++
            }
            cache[string] = DoubleLinkListNode(string)
            updateMostRecent(cache[string])
        }
//        else {
//           // replaceKey(string)
//        }
    }

    private fun insertElementInOldCache(string: String) {
        if (!oldCache.contains(string)) {
            if (currentSizeOfOld == Constant.OLD_STRING_MAX_SIZE) {
                removeLeastRecentFromOldElement()
            } else {
                currentSizeOfOld++
            }
            oldCache[string] = DoubleLinkListNode(string)
        } else {
            replaceKey(string)
        }
        updateMostRecentOld(oldCache[string])
    }

    private fun updateMostRecent(doubleLinkListNode: DoubleLinkListNode?) {
        linkedListHelper.setHeadTo(doubleLinkListNode)
    }

    private fun updateMostRecentOld(doubleLinkListNode: DoubleLinkListNode?) {
        olderLinkedListHelper.setHeadTo(doubleLinkListNode)
    }

    private fun removeLeastRecentElement() {
        val keyToRemove = linkedListHelper.tail?.ref
        linkedListHelper.removeTail()
        cache.remove(keyToRemove)
        insertElementInOldCache(keyToRemove!!)
    }

    private fun removeLeastRecentFromOldElement() {
        val keyToRemove = olderLinkedListHelper.tail?.ref
        olderLinkedListHelper.removeTail()
        oldCache.remove(keyToRemove)
        currentSizeOfOld--
    }

    private fun removeParticularElementFromOldCache(string: String) {
        val nodeToRemove = oldCache.get(string)
        val keyToRemove = nodeToRemove?.ref
        if (nodeToRemove != null) {
            olderLinkedListHelper.removeNode(nodeToRemove)
        }
        oldCache.remove(keyToRemove)
        currentSizeOfOld--
    }

    private fun replaceKey(string: String) {
        if (!cache.keys.contains(string)) {
            return
        }
        cache[string]?.ref = string
    }
}