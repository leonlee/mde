package org.blab.mde.ee.mixin;

import org.blab.mde.core.annotation.Mixin;

/**
 * The dynamic entities will be assembled in the fly during entity engine starting
 * to support customization and extension.<br/>
 */
@Mixin
public interface WithLongID extends WithID<Long> {
}
