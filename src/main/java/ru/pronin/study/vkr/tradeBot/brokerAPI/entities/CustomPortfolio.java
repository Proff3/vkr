package ru.pronin.study.vkr.tradeBot.brokerAPI.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomPortfolio {
    private List<CustomPortfolioPosition> positions = new ArrayList<>();
    public CustomPortfolio positions(List<CustomPortfolioPosition> positions) {
        this.positions = positions;
        return this;
    }

    public CustomPortfolio addPositionsItem(CustomPortfolioPosition positionsItem) {
        this.positions.add(positionsItem);
        return this;
    }

    /**
     * Get positions
     * @return positions
     **/
    public List<CustomPortfolioPosition> getPositions() {
        return positions;
    }
    public void setPositions(List<CustomPortfolioPosition> positions) {
        this.positions = positions;
    }
    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomPortfolio portfolio = (CustomPortfolio) o;
        return Objects.equals(this.positions, portfolio.positions);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(positions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Portfolio {\n");

        sb.append("    positions: ").append(toIndentedString(positions)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
