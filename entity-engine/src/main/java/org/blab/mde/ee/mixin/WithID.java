package org.blab.mde.ee.mixin;

import org.blab.mde.core.annotation.Arg;
import org.blab.mde.core.annotation.Delegate;
import org.blab.mde.core.annotation.Mixin;
import org.blab.mde.core.annotation.Self;
import org.blab.mde.ee.annotation.Field;

import javax.validation.constraints.NotNull;

import static org.blab.mde.core.util.Guarder.requireNotNull;


@Mixin
public interface WithID<T extends Comparable<T>> extends Comparable<WithID<T>> {
    @Field
    T getId();

    void setId(T id);

    @Override
    default int compareTo(@NotNull WithID<T> that) {
        requireNotNull(getId(), "can't compare null object");
        requireNotNull(that, "can't compare to null object");
        requireNotNull(that.getId(), "can't compare to null object");

        return getId().compareTo(that.getId());
    }

    @Delegate(name = "equals", parameterTypes = {Object.class})
    static boolean equals(@Self WithID self, @Arg(0) Object someone) {
        //noinspection unchecked
        return someone instanceof WithID
                && (self == someone || self.compareTo((WithID) someone) == 0);
    }
}
