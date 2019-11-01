package com.oo.resume.httptrace

import java.util.*

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-10-26 10:47
 *
 */
class XInMemoryHttpTraceRepository : XHttpTraceRepository {

    private var capacity = 100

    private var reverse = true

    private val traces = LinkedList<XHttpTrace>()

    /**
     * Flag to say that the repository lists traces in reverse order.
     * @param reverse flag value (default true)
     */
    fun setReverse(reverse: Boolean) {
        synchronized<Unit>(this.traces) {
            this.reverse = reverse
        }
    }

    /**
     * Set the capacity of the in-memory repository.
     * @param capacity the capacity
     */
    fun setCapacity(capacity: Int) {
        synchronized(lock = this.traces) {
            this.capacity = capacity
        }
    }

    override fun findAll(): List<XHttpTrace> {
        synchronized(this.traces) {
            return Collections.unmodifiableList(ArrayList(this.traces))
        }
    }

    override fun add(trace: XHttpTrace) {
        synchronized(this.traces) {
            while (this.traces.size >= this.capacity) {
                this.traces.removeAt(if (this.reverse) this.capacity - 1 else 0)
            }
            if (this.reverse) {
                this.traces.add(0, trace)
            } else {
                this.traces.add(trace)
            }
        }
    }

}