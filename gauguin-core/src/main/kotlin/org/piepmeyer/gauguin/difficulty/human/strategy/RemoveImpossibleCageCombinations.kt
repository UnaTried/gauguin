package org.piepmeyer.gauguin.difficulty.human.strategy

import org.piepmeyer.gauguin.difficulty.human.HumanSolverCache
import org.piepmeyer.gauguin.difficulty.human.HumanSolverStrategy
import org.piepmeyer.gauguin.difficulty.human.PossiblesReducer
import org.piepmeyer.gauguin.grid.Grid
import org.piepmeyer.gauguin.grid.GridCell

/**
 * Looks out if a cage's cells contain possibles which are not included in any
 * valid combination. If so, deletes these possibles out of all the cage's
 * cells.
 */
class RemoveImpossibleCageCombinations : HumanSolverStrategy {
    override fun fillCells(
        grid: Grid,
        cache: HumanSolverCache,
    ): Pair<Boolean, List<GridCell>?> {
        grid.cages
            .filter { it.cells.any { !it.isUserValueSet } }
            .forEach { cage ->
                val reducedPossibles = PossiblesReducer(cage).reduceToPossibleCombinations(cache.possibles(cage))

                if (reducedPossibles) {
                    return HumanSolverStrategy.successCellsChanged(cage.cells)
                }
            }

        return HumanSolverStrategy.nothingChanged()
    }
}
