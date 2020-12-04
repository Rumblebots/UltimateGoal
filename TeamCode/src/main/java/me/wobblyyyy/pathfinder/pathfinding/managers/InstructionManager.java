package me.wobblyyyy.pathfinder.pathfinding.managers;

import me.wobblyyyy.intra.ftc2.utils.Timed;
import me.wobblyyyy.intra.ftc2.utils.async.event.StringEvents;
import me.wobblyyyy.intra.ftc2.utils.jrep.ListWrapper;
import me.wobblyyyy.pathfinder.pathfinding.paths.instructions.Instruction;

import java.util.ArrayList;

/**
 * Instruction managing system, designed to keep track and handle all of
 * the instructions the pathfinder is using.
 *
 * <p>
 * Instructions are derived from paths. Instructions are where all the magic
 * really happens - obviously, a pathfinder uses paths, but these paths are
 * composed of instructions, and those instructions actually make the robot
 * move. Nothing that happens here is really too complicated, but oh well.
 * </p>
 *
 * @author Colin Robertson
 */
public class InstructionManager {
    /**
     * The current index of which instruction is being processed.
     *
     * <p>
     * This <b>NEEDS</b> to be updated any time an instruction is finished
     * processing or anything of the likes.
     * </p>
     */
    public int index = 0;

    /**
     * The length of the ListWrapper of instructions.
     *
     * <p>
     * This needs to be updated any time anything related to the size of
     * the ListWrapper containing all of the instructions which are currently
     * being worked with changes. When instructions are added or deleted, which
     * will probably happen pretty frequently, this needs to be changed.
     * </p>
     */
    public int length = 0;

    /**
     * All of the instructions which are currently being kept track of.
     */
    public ListWrapper<Instruction> instructions = new ListWrapper<>();

    /**
     * A list of all of the instructions which have already been
     * started, so to speak.
     */
    public ArrayList<Integer> hasBeenStarted = new ArrayList<>();

    /**
     * Run the list of instructions.
     *
     * <p>
     * This should be run as often as possible - instructions are designed to be
     * executed several dozen times per second. Ideally, this could be called every
     * repetition of the loop - that provides the most frequent updates, thus providing
     * the most accurate pathfinding.
     * </p>
     */
    public void runInstructions() {
        // Length/size is reduced by one, as size is different from
        // indexes - arrays, lists, and ArrayList(s) all start at 0.
        // Comparatively, size is an absolute number, starting at 1.
        // TRUE:
        // We're not at the end of the instructions list - we should
        // continue with execution of the list.
        // FALSE:
        // We're at the end of the list, and instructions no longer
        // need to be applied.
        if (!(index >= length - 1)) {
            Instruction instruction = instructions.list.get(index);
            if (instruction.conditionStart()) {
                startInstruction(instruction);
            }
            if (hasBeenStarted.contains(getHash(instruction))) {
                if (instruction.conditionContinue()) {
                    instruction.execute();
                }
                if (instruction.conditionStop()) {
                    hasBeenStarted.remove(instruction.hashCode());
                }
            }
        }
    }

    private void startInstruction(final Instruction instruction) {
        if (instruction.isTimed()) {
            StringEvents.schedule(
                    "pFinderMgr",
                    instruction.maxTime(),
                    0,
                    new Timed() {
                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    forceRemoveInstruction(instruction);
                                }
                            };
                        }
                    },
                    false
            );
        }
        hasBeenStarted.add(getHash(instruction));
    }

    /**
     * Forcibly remove an instruction.
     *
     * @param instruction the instruction to remove.
     */
    private void forceRemoveInstruction(Instruction instruction) {
        instructions.list.remove(instruction);
        hasBeenStarted.remove(getHash(instruction));
        length = instructions.list.size();
    }

    /**
     * Get the hash code of an instruction - useful in keeping track
     * of which instructions have been "started" and which ones have not.
     *
     * @param instruction the instruction to get the hash code of.
     * @return the hash code of the given instruction.
     */
    private int getHash(Instruction instruction) {
        return instruction.hashCode();
    }

    /**
     * Cancel all of the instructions.
     *
     * <p>
     * This ignores the "is cancellable" property of instructions and cancels them
     * all anyway. Y'know what they say - fuck those goddamn instructions.
     * I'm in a relatively vulgar mood right now, so this documentation might get
     * a little bit spicy.
     * </p>
     */
    public void cancelInstructions() {
        index = 0;
        length = 0;
        instructions = new ListWrapper<>();
        hasBeenStarted = new ArrayList<>();
    }

    /**
     * Add an instruction.
     *
     * @param instruction the instruction to be added.
     */
    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
        onListUpdate();
    }

    /**
     * Remove an instruction at a given index.
     *
     * @param target the index of the instruction to remove.
     */
    public void removeInstruction(int target) {
        instructions.list.remove(target);
        onListUpdate();
    }

    /**
     * Should be called every time the list is updated whatsoever.
     */
    private void onListUpdate() {
        length = instructions.list.size();
    }
}
