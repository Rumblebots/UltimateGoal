package me.wobblyyyy.pathfinder.fieldMapping.pathfinding;

/**
 * Basic directions to be used for pathfinding.
 *
 * <p>
 * I'm not even sure I'll end up using this!!
 * </p>
 * <p>
 * Rather than taking a pure-numbers approach pathfinding, I think
 * it would be easier to wrap one's head around the idea of pathfinding
 * using some letters as a guide.
 * </p>
 * <p>
 * Stylistic notes...
 * <pre>
 * Letter A represents any "prefix."
 * Letter B represents any "suffix."
 * A would be purely A - if A was forwards, it would be nothing but forwards.
 * B would be purely B - if B was rightwards, it would be nothing but rightwards.
 * AB would be a combination of both. Assuming A is 90deg and B is 0deg, AB is 45deg.
 * AAB would be 67.5deg - between AB and A.
 * ABB would be the same as AAB, except the opposite direction.
 * </pre>
 * </p>
 */
public enum PrimitiveDirections {
    /**
     * Purely forwards - 90deg or 2pi.
     */
    F,
    /**
     * Forwards and rightwards, a blend of them both.
     */
    FFR,
    /**
     * Mostly forwards - between the midpoint between
     * forward and forward-right.
     */
    FR,
    /**
     * Mostly rightwards.
     */
    FRR,
    /**
     * Purely rightwards.
     */
    R,
    /**
     * Rightwards, somewhat downwards.
     */
    RRB,
    /**
     * A combination of rightwards and downwards
     */
    RB,
    /**
     * Mostly backwards.
     */
    RBB,
    /**
     * Purely backwards.
     */
    B,
    /**
     * Mostly rightwards, somewhat leftwards.
     */
    BBL,
    /**
     * Both rightwards and leftwards.
     */
    BL,
    /**
     * Mostly leftwards, somewhat backwards.
     */
    BLL,
    /**
     * Purely leftwards.
     */
    L,
    /**
     * Mostly leftwards, somewhat forwards.
     */
    LLF,
    /**
     * A mix of both leftwards and forwards.
     */
    LF,
    /**
     * Mostly forwards, but somewhat leftwards.
     */
    LFF
}
